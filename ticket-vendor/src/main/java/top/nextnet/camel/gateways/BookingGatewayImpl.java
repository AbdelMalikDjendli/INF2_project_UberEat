package top.nextnet.camel.gateways;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.inject.ConfigProperty;


import java.io.IOException;

@ApplicationScoped
public class BookingGatewayImpl implements top.nextnet.service.BookingGateway {

    @Inject
    CamelContext context;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.vendorId")
    Integer vendorId;

    @Override
    public void sendBookingOrder(int standingCount, int seatingCount, int venueId) {
        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBody("direct:cli", new Booking(vendorId, venueId, seatingCount, standingCount));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
