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

    public void sendMenuToDarkkitchen(int idMenu) {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)
       ) {
            ObjectMapper objectMapper = new ObjectMapper();
            Menu menuModel = menuDAO.findMenuById(idMenu);
            fr.pantheonsorbonne.ufr27.miage.dto.Menu menuDto = new fr.pantheonsorbonne.ufr27.miage.dto.Menu(menuModel.getName(), menuModel.getPrice());
            String orderJson = objectMapper.writeValueAsString(menuDto);
            Message msg =context.createTextMessage(orderJson);
            context.createProducer().send(context.createTopic("sjms2:topic:M1.DK"), msg);
            Log.info("nouvelle commande :"+ idMenu);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
