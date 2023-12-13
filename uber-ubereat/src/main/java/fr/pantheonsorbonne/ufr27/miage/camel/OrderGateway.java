package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import fr.pantheonsorbonne.ufr27.miage.dto.Order;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.json.JsonObject;
import org.apache.camel.ProducerTemplate;
import jakarta.jms.*;

import java.io.IOException;
import java.io.Serializable;

public class OrderGateway {

    @Inject
    ConnectionFactory connectionFactory;
    public void sendOrder(Menu menu) {

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)
       ) {
            ObjectMapper objectMapper = new ObjectMapper();
            String orderJson = objectMapper.writeValueAsString(menu);
            context.createProducer().send(context.createTopic("sjms2:M1.darkkitchen"), orderJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
