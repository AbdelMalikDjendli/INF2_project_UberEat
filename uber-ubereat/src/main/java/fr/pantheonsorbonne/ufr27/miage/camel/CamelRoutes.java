package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.service.DkChoiceService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BarcodeDataFormat;
import org.apache.camel.spi.DataFormat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

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
    OrderDAO orderDAO;
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
            orderGateway.sendConfirmationToDeliveryMan( deliveryManName);

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

