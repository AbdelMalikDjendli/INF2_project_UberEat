package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.service.DkChoiceService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderServiceImpl;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {
    @Inject
    OrderService orderService;
    @Inject
    CamelContext camelContext;

    @Inject
    ChoiceProcessor choiceProcessor;

    @Override
    public void configure() throws Exception {



        camelContext.setTracing(true);


        from("sjms2:queue:M1.DK_ESTIMATION")
                .process(choiceProcessor);


        from("sjms2:queue:M1.DK_READY")
                .process(exchange -> {
                    String orderIdStr = exchange.getIn().getBody(String.class);
                    long orderId = Long.parseLong(orderIdStr);
                    Log.info("Notification de commande prête pour l'ID: " + orderId);
                    // Mettre à jour le statut de la commande dans la base de données
                    orderService.updateOrderStatusToReady(orderId);
                });


    }

    @ApplicationScoped
    private static class ChoiceProcessor implements Processor {

        @Inject
        DkChoiceService dkChoiceService;

        @Inject
        OrderGateway orderGateway;


        @Override
        public void process(Exchange exchange) throws Exception {
            dkChoiceService.resetEstimations();
            String body = exchange.getIn().getBody(String.class);
            //Une incrémente le nombre d'estimation reçu
            dkChoiceService.setNumberOfEstimation();
            int newEstimation;
            if(!body.equals("indisponible")){
                newEstimation = Integer.parseInt(body);
                //Si l'estimation reçu est plus courte que la précédente alors la stock dans estimation
                //et on stock le nom de la darkkitchen
                if(newEstimation<dkChoiceService.getMinEstimation()){
                    dkChoiceService.setMinEstimation(newEstimation);
                    dkChoiceService.setDkName(exchange.getMessage().getHeader("dk", String.class));
                }
            }
            //Si toutes les darkkitchen ont répondu alors on envoi la confirmation à la darkkitchen choisi
            if(dkChoiceService.getNumberOfEstimation()==1){
                orderGateway.sendConfirmationToDarkkitchen(dkChoiceService.getDkName());
            }
        }
    }
}

