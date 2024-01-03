package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.service.ConfirmationCodeService;
import fr.pantheonsorbonne.ufr27.miage.service.DeliveryManService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    CamelContext camelContext;
    @Inject
    DeliveryManProcessor deliveryManProcessor;

    @Inject
    DeliveryManService deliveryManService;

    @Inject
    OrderService orderService;

    @Inject
    OrderGateway orderGateway;

    @Inject
    ConfirmationCodeService confirmationCodeService;


    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:topic:M1.DELIVERY")
                .process(deliveryManProcessor)
                //content-based route
                .choice()
                .when(header("canTakeOrder"))
                .log("Le livreur est disponible et a le bon véhicule pour la distance de ${header.distance} km")
                .to("sjms2:queue:M1.LIVREUR_DISPO_CONFIRMATION")
                .otherwise()
                .to("sjms2:queue:M1.LIVREUR_INDISPO")
                .log("Le livreur n'est pas disponible ou il n'a pas le bon type de véhicule pour la distance de ${header.distance} km");


        from("sjms2:queue:M1." + deliveryManService.getNameDeliveryMan()).unmarshal().json(OrderDTO.class).process(exchange -> {
            //ajouter la commande à la bdd
            orderService.createOrder(exchange.getIn().getBody(OrderDTO.class).dkName());
            //le livreur n'est plus dispo
            deliveryManService.setDeliveryManIsAvaible(deliveryManService.getIdDeliveryMan(),false);
            //récupère la commande auprès de la DK
            orderGateway.askOrderToDK();
        });

        from("sjms2:queue:M1.CODE_RESPONSE_" + deliveryManService.getNameDeliveryMan())
                .process(exchange -> confirmationCodeService.setGoodCode(exchange.getMessage().getHeader("isGoodCode", String.class
                )));

    }



    @ApplicationScoped
    private static class DeliveryManProcessor implements Processor {

        @Inject
        DeliveryManService deliveryManService;

        @Override
        public void process(Exchange exchange) throws Exception {
            // Récupération de l'ID de la commande et de la distance
            int orderId = exchange.getIn().getHeader("orderId", Integer.class);
            int distance = exchange.getIn().getHeader("distance", Integer.class);

            //récup de la dispo et du véhicule du livreur
            boolean isAvailable = deliveryManService.isDeliveryManAvailable();
            String vehicleType = deliveryManService.getVehiculeDeliveryMan();

            // determine si le livreur peut prendre la co ( dispo + véhicule)
            boolean canTakeOrder = isAvailable &&
                    ((vehicleType.equals("vélo") && distance <= 10) || (vehicleType.equals("scooter") && distance > 10));

            // changer le header du message
            exchange.getIn().setHeader("canTakeOrder", canTakeOrder);
            // si il peut, on envoit son id et la co
            if (canTakeOrder) {
                exchange.getIn().setHeader("deliveryManName", deliveryManService.getNameDeliveryMan());
                exchange.getIn().setHeader("orderId", orderId);
                // son nom ?
            }

        }
    }
}

