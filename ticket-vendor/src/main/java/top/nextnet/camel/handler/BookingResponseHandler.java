package top.nextnet.camel.handler;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;


import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
public class BookingResponseHandler {

    @Handler
    public void onBookedResponseReceived(Exchange exchange) {
        Booking booking = exchange.getMessage().getBody(Booking.class);


        Collection<Integer> tansitionalTicktsId = new ArrayList<>(booking.getStandingTransitionalTicket().size() + booking.getSeatingTransitionalTicket().size());
        tansitionalTicktsId.addAll(booking.getSeatingTransitionalTicket());
        tansitionalTicktsId.addAll(booking.getStandingTransitionalTicket());


        if (tansitionalTicktsId.size() > 0) {
            Collection<ETicket> tickets = new ArrayList<>(tansitionalTicktsId.size());
            for (Integer ticketId : tansitionalTicktsId) {
                tickets.add(new ETicket(ticketId));
            }
            exchange.getMessage().setHeader("success", true);
            exchange.getMessage().setBody(tickets);
            return;
        }

        exchange.getMessage().

                setHeader("success", false);


    }
}
