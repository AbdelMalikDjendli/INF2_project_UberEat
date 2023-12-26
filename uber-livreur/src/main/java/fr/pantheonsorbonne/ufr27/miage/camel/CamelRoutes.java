package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
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


    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:topic:M1.DELIVERY")
                .process(deliveryManProcessor)
                .choice()
                .when(header("canTakeOrder"))
                .log("Le livreur est disponible et a le bon véhicule pour la distance de ${header.distance} km")
                .to("sjms2:queue:M1.LIVREUR_DISPO_CONFIRMATION")
                .otherwise()
                .log("Le livreur n'est pas disponible ou il n'a pas le bon type de véhicule pour la distance de ${header.distance} km");


        from("sjms2:queue:M1." + deliveryManService.getIdDeliveryMan()).unmarshal().json(OrderDTO.class).process(exchange -> {
            orderService.createOrder(exchange.getIn().getBody(OrderDTO.class).menu().name());
            Log.info("Commande en livraison");
        });

    }



    @ApplicationScoped
    private static class DeliveryManProcessor implements Processor {

        @Inject
        DeliveryManService deliveryManService;

        @Override
        public void process(Exchange exchange) throws Exception {
            // Récupération de l'ID de la commande et de la distance à partir des headers
            int orderId = exchange.getIn().getHeader("orderId", Integer.class);
            int distance = exchange.getIn().getHeader("distance", Integer.class);
            boolean isAvailable = deliveryManService.isDeliveryManAvailable();
            String vehicleType = deliveryManService.getVehiculeDeliveryMan();

            // determine si le livreur peut prendre la co
            boolean canTakeOrder = isAvailable &&
                    ((vehicleType.equals("vélo") && distance <= 10) || (vehicleType.equals("scooter") && distance > 10));

            // changer le header du message
            exchange.getIn().setHeader("canTakeOrder", canTakeOrder);
            if (canTakeOrder) {

                exchange.getIn().setHeader("deliveryManId", deliveryManService.getIdDeliveryMan());
                exchange.getIn().setHeader("orderId", orderId);
            }
        }
    }
}

