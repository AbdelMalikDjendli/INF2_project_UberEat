package fr.pantheonsorbonne.ufr27.miage.camel;


import com.fasterxml.jackson.databind.ObjectMapper;

import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import io.quarkus.logging.Log;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.Console;
import java.util.List;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:topic:M1.DK_ESTIMATION").process(new ChoiceProcessor());

    }

    private static class ChoiceProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            /*
            String estimation = exchange.getMessage().getBody(String.class);
            String dkName = exchange.getMessage().getHeader("darkKitchenName",String.class);
             */
            //Log.info("Nouvelle estimation reçu : "+estimation+" par "+dkName);
            Log.info("nouvelle estimation reçu");

            /*
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

             */
        }
    }
}

