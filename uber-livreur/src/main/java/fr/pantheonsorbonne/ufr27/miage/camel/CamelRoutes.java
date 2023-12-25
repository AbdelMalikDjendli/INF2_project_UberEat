package fr.pantheonsorbonne.ufr27.miage.camel;


import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        from("sjms2:topic:M1.DELIVERY")
                .choice()
                .when(header("distance").isLessThanOrEqualTo(10))
                .to("direct:assignBikeDelivery")
                .when(header("distance").isGreaterThan(10))
                .to("direct:assignScooterDelivery")
                .otherwise()
                .log("Aucun livreur disponible pour cette distance");

        from("direct:assignBikeDelivery")
                .log("Attribution de la livraison à un coursier à vélo pour une commande à ${header.distance} km de distance");
        // Plus de logique pour assigner le livreur à vélo

        from("direct:assignScooterDelivery")
                .log("Attribution de la livraison à un coursier en scooter pour une commande à ${header.distance} km de distance");
        // Plus de logique pour assigner le livreur en scooter


    }
}
