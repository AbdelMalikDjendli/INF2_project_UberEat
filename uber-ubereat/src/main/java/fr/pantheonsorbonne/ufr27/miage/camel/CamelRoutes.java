package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.service.DkChoiceService;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @Inject
    DkChoiceService dkChoiceService;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:topic:M1.DK_ESTIMATION")
                .process(new ChoiceProcessor(dkChoiceService));


    }

    private static class ChoiceProcessor implements Processor {

        private final DkChoiceService dkChoiceService ;
        private final List<String> estimations = new ArrayList<>();
        private ChoiceProcessor(DkChoiceService dkChoiceService) {
            this.dkChoiceService = dkChoiceService;
        }

        @Override
        public void process(Exchange exchange) throws Exception {
            estimations.add(exchange.getIn().getBody(String.class));
            String fastestDK = dkChoiceService.chooseFastestDarkKitchen(estimations);
            exchange.getIn().setBody(fastestDK);
            Log.info("La Dark Kitchen la plus rapide sélectionnée : " + fastestDK);
            estimations.clear();
        }
    }
}

