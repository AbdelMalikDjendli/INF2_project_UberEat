package fr.pantheonsorbonne.ufr27.miage.camel;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAOImpl;
import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import fr.pantheonsorbonne.ufr27.miage.service.EstimationService;
import fr.pantheonsorbonne.ufr27.miage.service.EstimationServiceImpl;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);

        from("sjms2:topic:"+ jmsPrefix +"darkkitchen").unmarshal().json(Menu.class)
                .process(new EstimationProcessor())
                .to("sjms2:queue:" + jmsPrefix + "estimation");

    }

    private static class EstimationProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            MenuDAO menuDAO = new MenuDAOImpl();
            EstimationService estimationService = new EstimationServiceImpl();
            Menu menuFromJms = exchange.getMessage().getMandatoryBody(Menu.class);
            List<fr.pantheonsorbonne.ufr27.miage.model.Menu> allMenu = menuDAO.getAllMenu();
            List<fr.pantheonsorbonne.ufr27.miage.model.Menu> allMenuFiltered =  allMenu.stream()
                    .filter(menu -> menu.getName().equals(menuFromJms.name()))
                    .toList();
            String estimation = allMenuFiltered.isEmpty() ? "indisponible" : estimationService.getRandomEstimation();
            String activeProfile = ProfileManager.getActiveProfile();
            exchange.getMessage().setBody(estimation);
            exchange.getMessage().setHeader("darkKitchenId",activeProfile);
        }
    }
}
