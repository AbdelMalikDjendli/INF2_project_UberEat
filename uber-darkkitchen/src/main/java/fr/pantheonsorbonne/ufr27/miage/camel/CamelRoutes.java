package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.service.EstimationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import java.util.List;


@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    EstimationProcessor estimationProcessor;

    @Inject
    DkDAO dkDAO;

    @Inject
    OrderDAO orderDAO;

    @Inject
    MenuDAO menuDAO;

    @Override
    public void configure() throws Exception {
        from("sjms2:topic:M1.DK").unmarshal().json(OrderDTO.class)
                .process(estimationProcessor)
                .to("sjms2:queue:M1.DK_ESTIMATION");

        from("sjms2:queue:M1." + dkDAO.getDKName()).unmarshal().json(OrderDTO.class).process(exchange -> {
            orderDAO.newOrder(menuDAO.findMenuByName(exchange.getIn().getBody(OrderDTO.class).menu().name()));
            Log.info("Commande en préparation");
        });

    }


        @ApplicationScoped
    private static class EstimationProcessor implements Processor {
        @Inject
        EstimationService estimationService;
        @Inject
        DkDAO dkDAO;

        @Inject
        MenuDAO menuDAO;

        @Override
        public void process(Exchange exchange) throws Exception {
            OrderDTO newOrder = exchange.getIn().getBody(OrderDTO.class);
            String nameMenuOrdered = newOrder.menu().name();
            List<Menu> allMenu = menuDAO.getAllMenu();
            List<Menu> allMenuFiltered = allMenu.stream()
                    .filter(menu -> menu.getName().equals(nameMenuOrdered)).toList();

            String estimation = allMenuFiltered.isEmpty()?"indisponible": estimationService.getRandomEstimation();
            String darkKitchenName = dkDAO.getDKName();
            String response = darkKitchenName + ":" + estimation;
            exchange.getIn().setBody(estimation);
            exchange.getIn().setHeader("dk", darkKitchenName);
            Log.info("Estimation envoyée: " + response);
        }


    }
}
