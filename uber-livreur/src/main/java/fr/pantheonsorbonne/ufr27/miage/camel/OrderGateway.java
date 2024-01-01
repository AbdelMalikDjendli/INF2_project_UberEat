package fr.pantheonsorbonne.ufr27.miage.camel;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;

@ApplicationScoped
public class OrderGateway {

    @Inject
    ConnectionFactory connectionFactory;

    public void takeOrderFromDK(String dkName) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage message = context.createTextMessage("Start Delivery process");
            context.createProducer().send(context.createQueue("M1.ASK_ORDER"), message);
            Log.info("Le livreur demande la commande à la Darkkitchen");
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi du message 'Le livreur demande la commande à la DK': ", e);
        }
    }
}
