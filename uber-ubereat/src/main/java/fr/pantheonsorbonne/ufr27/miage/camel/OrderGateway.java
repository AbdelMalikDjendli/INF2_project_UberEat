package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDto;
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
            MenuDto menuDto = new MenuDto(menuModel.getName(), menuModel.getPrice());
            String orderJson = objectMapper.writeValueAsString(menuDto);
            Message msg = context.createTextMessage(orderJson);
            context.createProducer().send(context.createTopic("sjms2:topic:M1.DK"), msg);
            Log.info("Commande envoyée au topic 'M1.DK': " + orderJson);

        } catch (IOException e) {
            e.printStackTrace();
            Log.error("Erreur lors de l'envoi de la commande: ", e);
        }
    }

}
