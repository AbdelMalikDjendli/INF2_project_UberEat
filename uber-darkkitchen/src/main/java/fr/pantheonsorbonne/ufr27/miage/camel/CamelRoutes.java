package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.service.DkService;
import fr.pantheonsorbonne.ufr27.miage.service.EstimationService;
import fr.pantheonsorbonne.ufr27.miage.service.MenuService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    EstimationProcessor estimationProcessor;

    @Inject
    OrderService orderService;

    @Inject
    DkService dkService;

    @Inject
    OrderGateway orderGateway;


    @Override
    public void configure() throws Exception {

        //envoyer estimation
        from("sjms2:topic:M1.DK").unmarshal().json(OrderDTO.class)
                .process(estimationProcessor)
                .to("sjms2:queue:M1.DK_ESTIMATION");

        //confirmation + création de la commande
        from("sjms2:queue:M1." + dkService.getCurrentDkName()).unmarshal().json(OrderDTO.class).process(exchange -> {
            orderService.createOrder(exchange.getIn().getBody(OrderDTO.class).menu().name());
            Log.info("Commande en préparation");
        });

        //demande de recupération de la commande par le livreur
        from("sjms2:queue:M1.ASK_ORDER").process(exchange -> {
            orderGateway.startDeliveryEvent();
            Log.info("Commande en préparation");
        });

    }


    @ApplicationScoped
    private static class EstimationProcessor implements Processor {
        @Inject
        EstimationService estimationService;

        @Inject
        MenuService menuService;

        @Inject
        DkService dkService;

        @Override
        public void process(Exchange exchange) throws Exception {
            OrderDTO newOrder = exchange.getIn().getBody(OrderDTO.class);
            String nameMenuOrdered = newOrder.menu().name();
            String estimation = menuService.isMenuPreparedByDk(nameMenuOrdered) ? estimationService.getRandomEstimation() : "indisponible";
            String darkKitchenName = dkService.getCurrentDkName();
            exchange.getIn().setBody(estimation);
            exchange.getIn().setHeader("dk", darkKitchenName);
            Log.info(darkKitchenName + " estime son temps de préparation pour " + nameMenuOrdered + " à " + estimation + " minutes");
        }



    }
}
