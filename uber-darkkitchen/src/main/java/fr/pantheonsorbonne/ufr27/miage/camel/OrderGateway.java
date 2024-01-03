package fr.pantheonsorbonne.ufr27.miage.camel;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

@ApplicationScoped
public class OrderGateway {

    @Inject
    ConnectionFactory connectionFactory;

    public void sendOrderReadyMessage(long orderId) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage message = context.createTextMessage(Long.toString(orderId));
            context.createProducer().send(context.createQueue("M1.DK_READY"), message);
            Log.info("Message envoyé à Uber Eats : Commande " + orderId + " prête");
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi du message 'Commande prête': ", e);
        }
    }

    // Event Message envoyé à UBER
    public void startDeliveryEvent() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage message = context.createTextMessage("Delivery has started");
            context.createProducer().send(context.createQueue("M1.ORDER_GIVEN_TO_DELIVERYMAN"), message);
            //Log.info("Message envoyé à Uber Eats : Commande " + orderId + " prête");
            Log.info("Commande En Route");
        } catch (JMSRuntimeException e) {
            Log.error("Error : ORDER_GIVEN_TO_DELIVERYMAN", e);
        }
    }
}
