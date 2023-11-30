package top.nextnet.camel.gateways;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import top.nextnet.cli.UserInterface;


import java.util.Collection;

@ApplicationScoped
public class TicketingServiceImpl implements top.nextnet.service.TicketingService {

    @Inject
    UserInterface ihm;

    @Override
    public Collection<ETicket> fillTicketsWithCustomerInformations(Collection<ETicket> tickets) {
        ihm.showSuccessMessage("Venue Booked, please provide customer information for ticketing");

        String fname = ihm.getCustomerFirstName();
        String lname = ihm.getCustomerLastName();
        String email = ihm.getCustomerEmail();

        for (ETicket ticket : tickets) {
            ticket.setEmail(email);
            ticket.setFname(fname);
            ticket.setLname(lname);
        }

        return tickets;
    }

    @Override
    public void notifyCreatedTicket(String ticketKey) {
        ihm.showSuccessMessage("Ticket emited with key" + ticketKey);
    }
}
