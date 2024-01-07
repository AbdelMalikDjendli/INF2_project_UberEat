package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.service.ConfirmationCodeService;
import fr.pantheonsorbonne.ufr27.miage.service.DeliveryManService;
import fr.pantheonsorbonne.ufr27.miage.service.DkChoiceService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.user")
    String smtpUser;
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.password")
    String smtpPassword;
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.host")
    String smtpHost;
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.port")
    String smtpPort;
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.from")
    String smtpFrom;

    @Inject
    CamelContext camelContext;

    @Inject
    ChoiceProcessor choiceProcessor;

    @Inject
    DeliveryProcessor deliveryProcessor;

    @Inject
    NoAvailableDeliverersProcessor noAvailableDeliverersProcessor;

    @Inject
    UpdateStatusProcessor updateStatusProcessor;

    @Inject
    ConfirmationCodeProcessor confirmationCodeProcessor;


    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:queue:M1.DK_ESTIMATION")
                .process(choiceProcessor);

        //récéption des livreurs dispo
        from("sjms2:queue:M1.LIVREUR_DISPO_CONFIRMATION")
                .process(deliveryProcessor);

        //récéption des livreurs indispo
        from("sjms2:queue:M1.LIVREUR_INDISPO")
                .process(noAvailableDeliverersProcessor);

        //Dk nous informe que le livreur à récupéré la commande
        from("sjms2:queue:M1.ORDER_GIVEN_TO_DELIVERYMAN")
                .process(updateStatusProcessor);


        //Valide ou non la livraison
        from("sjms2:queue:M1.CONFIRMATION_CODE")
                .process(confirmationCodeProcessor).choice()
                .when(header("dmName").isEqualTo("Paul"))
                .to("sjms2:queue:M1.CODE_RESPONSE_Paul").otherwise()
                .to("sjms2:queue:M1.CODE_RESPONSE_Pierre");


        from("sjms2:queue:M1.FACTURE")
                .doTry()
                .setHeader("subject", simple("Votre Facture | Commande UberEat"))
                .setHeader("to", simple("lindabessah1@gmail.com"))
                //.to("smtps:" + smtpHost + ":" + smtpPort + "?username=" + smtpFrom + "&password=" + smtpPassword );
                .to("smtps://smtp.gmail.com:465?username=projetjava95@gmail.com&password=gbqe hnue xtsu ttuz");

    }

    @ApplicationScoped
    private static class ConfirmationCodeProcessor implements Processor {

        @Inject
        OrderGateway orderGateway;

        @Inject
        ConfirmationCodeService confirmationCodeService;

        @Inject
        DeliveryManService deliveryManService;

        @Inject
        OrderService orderService;


        @Override
        public void process(Exchange exchange) throws Exception {
            String body = exchange.getIn().getBody(String.class);
            String correctCode = confirmationCodeService.getCurrentCode();
            String dmName = exchange.getMessage().getHeader("dmName", String.class);
            boolean isGoodDm = deliveryManService.isGoodDm(dmName);
            if (isGoodDm && body.equals(correctCode)) {
                exchange.getMessage().setHeader("isGoodCode", "true");
                //mettre à jour le statut du livreur
                deliveryManService.setDeliveryManStatus(dmName, true);
                //mettre à jour le statut de la commande
                orderService.updateOrderStatus("Livrée");
                Log.info("Commande livrée");
                //Envoyer facture
                orderGateway.sendInvoice(orderService.getCurrentOrder());
            } else {
                exchange.getMessage().setHeader("isGoodCode", "false");
            }
        }
    }

    @ApplicationScoped
    private static class ChoiceProcessor implements Processor {

        @Inject
        DkChoiceService dkChoiceService;

        @Inject
        OrderGateway orderGateway;


        @Override
        public void process(Exchange exchange) throws Exception {
            String body = exchange.getIn().getBody(String.class);
            //Une incrémente le nombre d'estimation reçu
            dkChoiceService.setNumberOfEstimation();
            int newEstimation;
            if (!body.equals("indisponible")) {
                newEstimation = Integer.parseInt(body);
                //Si l'estimation reçu est plus courte que la précédente alors la stock dans estimation
                //et on stock le nom de la darkkitchen
                if (newEstimation < dkChoiceService.getMinEstimation()) {
                    dkChoiceService.setMinEstimation(newEstimation);
                    dkChoiceService.setDkName(exchange.getMessage().getHeader("dk", String.class));
                }
            }


            //Si toutes les darkkitchen ont répondu alors on envoi la confirmation à la darkkitchen choisi
            if (dkChoiceService.getNumberOfEstimation() == 2) {

                if (dkChoiceService.getDkName() != null) {
                    orderGateway.sendConfirmationToDarkkitchen(dkChoiceService.getDkName());
                } else {
                    Log.info("Pas de restaurant disponible");
                }
                //remettre à 0
                dkChoiceService.resetEstimations();
            }
        }
    }

    @ApplicationScoped
    private static class DeliveryProcessor implements Processor {

        @Inject
        OrderGateway orderGateway;


        @Override
        public void process(Exchange exchange) throws Exception {
            // pr le 1er livreur :
            //récupère le name
            String deliveryManName = exchange.getIn().getHeader("deliveryManName", String.class);
            // Appeler la méthode pour confirmer le premier livreur
            orderGateway.sendConfirmationToDeliveryMan(deliveryManName);

        }
    }

    @ApplicationScoped
    private static class NoAvailableDeliverersProcessor implements Processor {

        @Inject
        OrderService orderService;

        Map<Long, Integer> indisponibleCounters = new ConcurrentHashMap<>();

        @Override
        public void process(Exchange exchange) throws Exception {

            //récup id commande
            Long orderId = exchange.getIn().getHeader("orderId", Long.class);
            //incrémenter le compteur
            indisponibleCounters.put(orderId, indisponibleCounters.getOrDefault(orderId, 0) + 1);

            //récup le nb de livreurs
            int totalDeliveryMen = orderService.countTotalDeliveryMen();
            if (indisponibleCounters.get(orderId) == totalDeliveryMen) {
                // Tous les livreurs sont indisponibles pour cette commande
                Log.info("Aucun livreur disponible pour la commande ID: " + orderId);
                orderService.noneDeliveryManUpdate(orderId);
                // Réinitialiser le compteur pour cette commande
                indisponibleCounters.remove(orderId);

            }
        }
    }

    @ApplicationScoped
    private static class UpdateStatusProcessor implements Processor {

        @Inject
        OrderService orderService;

        @Override
        public void process(Exchange exchange) throws Exception {
            orderService.updateOrderStatus("en cours de livraison");
            Log.info("Commande en cours de livraison");
        }
    }
}

