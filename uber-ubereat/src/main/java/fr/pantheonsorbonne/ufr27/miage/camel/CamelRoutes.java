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
    OrderGateway orderGateway;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        camelContext.setTracing(true);


        from("sjms2:queue:M1.DK_ESTIMATION")
                .process(new ChoiceProcessor(dkChoiceService,orderGateway));


    }

    @ApplicationScoped
    private static class ChoiceProcessor implements Processor {

        private final DkChoiceService dkChoiceService ;
        private final OrderGateway orderGateway;


        private int estimation = 1000;
        private int response = 0;

        public ChoiceProcessor(DkChoiceService dkChoiceService, OrderGateway orderGateway) {

            this.dkChoiceService = dkChoiceService;
            this.orderGateway = orderGateway;
        }

        @Override
        public void process(Exchange exchange) throws Exception {
            int newEstimation = Integer.parseInt(exchange.getIn().getBody(String.class));
            response = response + 1;
            if(newEstimation<estimation){
                estimation = newEstimation;
                dkChoiceService.setDkQueue(exchange.getMessage().getHeader("dk", String.class));
            }

            if(response==1){
                orderGateway.sendConfirmationToDarkkitchen(dkChoiceService.getDkQueue());
                Log.info("Confirmation envoyé à :"+ dkChoiceService.getDkQueue());
            }


        }
    }
}

