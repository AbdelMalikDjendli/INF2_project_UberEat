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
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;


@ApplicationScoped
public class OrderGateway {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    MenuDAO menuDAO;

    @Inject
    OrderDAO orderDAO;

    @Inject
    DkDAO dkDAO;

    public void sendOrderToDarkkitchen(long orderId) throws JsonProcessingException {
        Order orderModel = orderDAO.findOrderById(orderId);
        MenuDTO menuDto = new MenuDTO(orderModel.getMenu().getName(), orderModel.getMenu().getDescription());
        OrderDTO orderDTO = new OrderDTO(orderModel.getStatus(), menuDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage msg = context.createTextMessage(orderJson);
            //msg.setJMSCorrelationID(Long.toString(menuId));
            context.createProducer().send(context.createTopic("M1.DK"), msg);
            Log.info("ID de la commande envoyé au topic 'M1.DK': " + orderModel.getId());
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi de l'ID de la commande: ", e);
        }
    }

    public void sendConfirmationToDarkkitchen(String dkName) throws JsonProcessingException {
        Order orderModel = orderDAO.getLastOrder();
        orderDAO.updateStatus(orderModel.getId(),"en cours de préparation");
        orderDAO.addDarkKitchen(orderModel.getId(),dkDAO.findDKByName(dkName));
        MenuDTO menuDto = new MenuDTO(orderModel.getMenu().getName(), orderModel.getMenu().getDescription());
        OrderDTO orderDTO = new OrderDTO(orderModel.getStatus(), menuDto);
        ObjectMapper objectMapper = new ObjectMapper();
        String orderJson = objectMapper.writeValueAsString(orderDTO);
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage msg = context.createTextMessage(orderJson);
            //msg.setJMSCorrelationID(Long.toString(menuId));
            context.createProducer().send(context.createQueue("M1."+dkName), msg);
            Log.info("ID de la confirmation a été envoyé à ': " + dkName);
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi de la confirmation: ", e);
        }
    }

}
