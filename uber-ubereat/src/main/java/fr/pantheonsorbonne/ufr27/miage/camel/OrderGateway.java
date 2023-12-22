package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@ApplicationScoped
public class OrderGateway {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    ConnectionFactory connectionFactory;

    @Inject
    MenuDAO menuDAO;

    public void sendOrderIdToDarkkitchen(long orderId) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage msg = context.createTextMessage(Long.toString(orderId));
            msg.setJMSCorrelationID(Long.toString(orderId));
            context.createProducer().send(context.createTopic("M1.DK"), msg);
            Log.info("ID de la commande envoyé au topic 'M1.DK': " + orderId);
        } catch (JMSRuntimeException | JMSException e) {
            Log.error("Erreur lors de l'envoi de l'ID de la commande: ", e);
        }
    }

}
