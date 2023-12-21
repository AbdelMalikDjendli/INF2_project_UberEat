package fr.pantheonsorbonne.ufr27.miage.camel;


import com.fasterxml.jackson.databind.ObjectMapper;

import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import io.quarkus.logging.Log;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
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


        from("sjms2:topic:M1.DK_ESTIMATION").process(new ChoiceProcessor())
                .log(LoggingLevel.INFO, "Message reçu dans la route 'M1.DK_ESTIMATION'");

    }

    private static class ChoiceProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            String estimation = exchange.getIn().getBody(String.class);
            Log.info("Estimation de temps reçue : " + estimation);
        }
    }
}

