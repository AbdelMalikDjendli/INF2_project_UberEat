package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

import java.util.Random;


@ApplicationScoped
public class OrderGateway {


    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    OrderService orderService;

    public void sendOrderToDarkkitchen(long orderId) throws JsonProcessingException {
        OrderDTO orderDTO = orderService.getOrderDTOFromModel(orderId);
        //On convertit l'orderDTO en json
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            //On créer un message contenant l'orderDTO sous forme de json
            TextMessage msg = context.createTextMessage(orderJson);
            //On envoie l'order aux darkKitchen via le topic M1.DK
            context.createProducer().send(context.createTopic("M1.DK"), msg);
            Log.info("Nouvelle commande disponible (recherche de darkKitchen): " + orderDTO.menu().name());
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi de la commande aux darkkitchen: ", e);
        }
    }

    public void sendConfirmationToDarkkitchen(String dkName) throws JsonProcessingException {
        //On récupère la commande - mettre à jour la bdd
        Order orderModel = orderService.dkFoundUpdate(dkName);
        //On convertit l'order en orderDTO
        OrderDTO orderDTO = orderService.getOrderDTOFromModel(orderModel.getId());
        //On convertit l'orderDTO en Json
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            //On envoie la commande à la darkkitchen choisi en guise de confirmation
            TextMessage msg = context.createTextMessage(orderJson);
            context.createProducer().send(context.createQueue("M1."+dkName), msg);
            Log.info("La darkkitchen choisi est :"+ dkName);

            //envoyer commande aux livreurs
            sendOrderToDeliveryMen(orderModel.getId(),dkName);
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi de la confirmation: ", e);
        }
    }

    public void sendOrderToDeliveryMen(long orderId, String dkName) {
        Random random = new Random();
        int randomDistance = random.nextInt(21) + 1; // Générer une distance aléatoire

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            // Créer un message contenant l'ID de la commande,le nom de la Dark Kitchen et la distance
            TextMessage message = context.createTextMessage();

            message.setLongProperty("orderId", orderId); //à voir si utile ?
            message.setStringProperty("dkName", dkName);
            message.setIntProperty("distance", randomDistance);

            // Envoyer le message au topic pour les livreurs
            context.createProducer().send(context.createTopic("M1.DELIVERY"), message);
            Log.info("Commande ID: " + orderId + " envoyée aux livreurs. Dark Kitchen: " + dkName + ", Distance : " + randomDistance + " km");
        } catch (JMSRuntimeException | JMSException e) {
            Log.error("Erreur lors de l'envoi de la commande aux livreurs: ", e);
        }
    }


    public void sendConfirmationToDeliveryMan( String deliveryManName) throws JsonProcessingException {
        //On récupère la commande - mettre à jour la bdd
        Order orderModel = orderService.deliveryManUpdate(deliveryManName);
        //On convertit l'order en orderDTO
        OrderDTO orderDTO = orderService.getOrderDTOFromModel(orderModel.getId());
        //On convertit l'orderDTO en Json
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        try(JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            //On envoie la commande au livreur  en guise de confirmation
            TextMessage msg = context.createTextMessage(orderJson);
            context.createProducer().send(context.createQueue("M1."+deliveryManName), msg);
            Log.info("Message de confirmation envoyé au livreur  " + deliveryManName + " pour la commande ID: " + orderModel.getId());
        }
    }

    public void sendInvoice(Order order) throws JsonProcessingException {
        /*
        OrderDTO orderDTO = orderService.getOrderDTOFromModel(orderId);
        //On convertit l'orderDTO en json
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);
         */
        Menu menu = order.getMenu();

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            //On créer un message contenant l'orderDTO sous forme de json
            TextMessage msg = context.createTextMessage("Votre commande : "+menu.getName()+
                    "\n Montant total : "+menu.getPrice() +" € \n" +
                    "Merci pour votre commande chez Uber Eat, à très bientôt !");
            //On envoie l'order aux darkKitchen via le topic M1.DK
            context.createProducer().send(context.createQueue("M1.FACTURE"), msg);
            Log.info("Facture envoyé");
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi de la facture: ", e);
        }
    }
}
