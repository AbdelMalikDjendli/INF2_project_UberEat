package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.service.DeliveryManService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.*;


@ApplicationScoped
public class OrderGateway {


    @Inject
    ConnectionFactory connectionFactory;


    @Inject
    DeliveryManService deliveryManService;

    public void askOrderToDK() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage msg = context.createTextMessage("ask to take order");
            context.createProducer().send(context.createQueue("M1.ASK_ORDER"), msg);
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de la demande de la commande par le livreur: ", e);
        }
    }

    public void sendConfirmationCode(String code) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            TextMessage msg = context.createTextMessage(code);
            msg.setStringProperty("dmName", deliveryManService.getNameDeliveryMan());
            context.createProducer().send(context.createQueue("M1.CONFIRMATION_CODE"), msg);
        } catch (JMSRuntimeException e) {
            Log.error("Erreur lors de l'envoi du code de confirmation: ", e);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

}
