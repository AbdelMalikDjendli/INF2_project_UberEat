package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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
        //On récupère la commande
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
            sendOrderToDeliveryMen(orderModel.getId(),dkName);
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi de la confirmation: ", e);
        }
    }

    public void sendOrderToDeliveryMen(long orderId, String dkName) {
        Random random = new Random();
        int randomDistance = random.nextInt(20);

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage message = context.createTextMessage(Long.toString(orderId));
            message.setStringProperty("distance", Integer.toString(randomDistance));
            context.createProducer().send(context.createTopic("M1.DELIVERY"), message);
            Log.info("Commande envoyée aux livreurs. distance : " + randomDistance + " km");
        } catch (JMSRuntimeException | JMSException e) {
            Log.error("Erreur lors de l'envoi de la commande aux livreurs: ", e);
        }
    }



}
