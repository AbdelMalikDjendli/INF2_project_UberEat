package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

public class OrderGateway {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;
    @Inject
    ConnectionFactory connectionFactory;
    public void sendMenuToDarkkitchen(Menu menu) {

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)
       ) {
            ObjectMapper objectMapper = new ObjectMapper();
            String orderJson = objectMapper.writeValueAsString(menu);
            context.createProducer().send(context.createTopic("sjms2:topic:"+ jmsPrefix +"darkkitchen"), orderJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
