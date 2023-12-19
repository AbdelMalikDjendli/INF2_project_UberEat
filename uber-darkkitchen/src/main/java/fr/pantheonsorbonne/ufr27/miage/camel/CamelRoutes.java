package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import fr.pantheonsorbonne.ufr27.miage.service.EstimationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

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

        from("sjsms2:topic:M1.DK").unmarshal().json(Menu.class)
                .process(new EstimationProcessor())
                .to("sjsms2:topic:M1.DK_ESTIMATION");

    }

    @ApplicationScoped
    private static class EstimationProcessor implements Processor {
        @Inject
        DkDAO dkDAO;

        @Inject
        MenuDAO menuDAO;

        @Inject
        EstimationService estimationService;


        @Override
        public void process(Exchange exchange) throws Exception {
            Menu menuFromJms = exchange.getMessage().getMandatoryBody(Menu.class);
            String nameMenuFromJms = menuFromJms.name();
            Log.info("nouvelle comande reçu :"+ nameMenuFromJms);
            List<fr.pantheonsorbonne.ufr27.miage.model.Menu> allMenu = menuDAO.getAllMenu();
            List<fr.pantheonsorbonne.ufr27.miage.model.Menu> allMenuFiltered =  allMenu.stream()
                    .filter(menu -> menu.getName().equals(nameMenuFromJms))
                    .toList();
            String estimation = allMenuFiltered.isEmpty() ? "indisponible" : estimationService.getRandomEstimation();
           // String activeProfile = ProfileManager.getActiveProfile();
            exchange.getMessage().setBody(estimation);
            exchange.getMessage().setHeader("darkKitchenName",dkDAO.getDKName());
        }
    }
}
