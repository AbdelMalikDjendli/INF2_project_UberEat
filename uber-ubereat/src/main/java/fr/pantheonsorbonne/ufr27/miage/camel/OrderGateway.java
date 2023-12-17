package fr.pantheonsorbonne.ufr27.miage.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.inject.Inject;
import jakarta.jms.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

public class OrderGateway {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;
    @Inject
    ConnectionFactory connectionFactory;
    public void sendMenuToDarkkitchen(int idMenu) {

        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)
       ) {
            ObjectMapper objectMapper = new ObjectMapper();
            MenuDAO menuDAO = new MenuDAOImpl();
            Menu menuModel = menuDAO.findMenuById(idMenu);
            fr.pantheonsorbonne.ufr27.miage.dto.Menu menuDto = new fr.pantheonsorbonne.ufr27.miage.dto.Menu(menuModel.getName(), menuModel.getPrice());
            String orderJson = objectMapper.writeValueAsString(menuDto);
            context.createProducer().send(context.createTopic("sjms2:topic:"+ jmsPrefix +"darkkitchen"), orderJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
