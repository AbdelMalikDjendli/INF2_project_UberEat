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

}
