package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import fr.pantheonsorbonne.ufr27.miage.service.EstimationService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

import static org.apache.camel.support.DefaultExchangeHolder.unmarshal;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    EstimationProcessor estimationProcessor;

    @Override
    public void configure() throws Exception {
        from("sjms2:topic:M1.DK")
                .process(estimationProcessor)
                .to("sjms2:topic:M1.DK_ESTIMATION");
    }



@ApplicationScoped
    private static class EstimationProcessor implements Processor {
    @Inject
    EstimationService estimationService;
    @Inject
    DkDAO dkDAO;
        @Override
        public void process(Exchange exchange) throws Exception {
            String estimation = estimationService.getRandomEstimation();
            int darkKitchenId = dkDAO.getDKId();
            String response = darkKitchenId + ":" + estimation;
            exchange.getIn().setBody(response);
            Log.info("Estimation envoyée: " + response);
        }


    }
}
