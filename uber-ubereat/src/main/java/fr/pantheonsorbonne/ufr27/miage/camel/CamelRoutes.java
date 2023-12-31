package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
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
import org.apache.camel.model.dataformat.BarcodeDataFormat;
import org.apache.camel.spi.DataFormat;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    CamelContext camelContext;

    @Inject
    ChoiceProcessor choiceProcessor;

    @Inject
    UpdateStatusProcessor updateStatusProcessor;
    @Inject
    OrderDAO orderDAO;
    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:queue:M1.DK_ESTIMATION")
                .process(choiceProcessor);

        // A FAIRE AVEC LE QRCODE

        from("sjms2:queue:M1.ORDER_GIVEN_TO_DELIVERYMAN")
                .process(updateStatusProcessor); //deliveryProcess

        /*
        from("sjms2:queue:M1.PROCESS_BARCODE_SCAN")
                .log("Code-barres scanné: ${header.CamelBarcodeData}")
                // Ajoutez la logique pour mettre à jour la base de données ou effectuer d'autres actions
                .to("direct:updateDatabase");
        */
    }

    @ApplicationScoped
    private static class ChoiceProcessor implements Processor {

        @Inject
        DkChoiceService dkChoiceService;

        @Inject
        OrderGateway orderGateway;

        @Override
        public void process(Exchange exchange) throws Exception {
            //remettre à 0
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
                if(dkChoiceService.getDkName()!=null) {
                    orderGateway.sendConfirmationToDarkkitchen(dkChoiceService.getDkName());
                }
                else{
                    Log.info("Pas de restaurant disponible");
                }
                dkChoiceService.resetEstimations();
            }
        }
    }


    @ApplicationScoped
    private static class UpdateStatusProcessor implements Processor {

        @Inject
        OrderService orderService;

        @Override
        public void process(Exchange exchange) throws Exception {
            String newStatus = orderService.statusDelivering();
            Log.info("Mise à jour du statut de la commande : " + "->"  + newStatus);
        }
    }


    /*
    public class BarcodeRoute extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("direct:generateBarcode")
                    .setHeader("CamelBarcodeData", constant("VotreDonneeAEncoder"))
                    .to("barcode:code128?width=200&height=100")
                    .to("direct:sendToJMSQueue"); // Remplacez cela par votre logique JMS
        }
    }
    */

}

