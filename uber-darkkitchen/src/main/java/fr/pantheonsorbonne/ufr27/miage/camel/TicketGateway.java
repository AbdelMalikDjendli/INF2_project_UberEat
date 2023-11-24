package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dao.NoSuchTicketException;
import fr.pantheonsorbonne.ufr27.miage.dto.CancelationNotice;
import fr.pantheonsorbonne.ufr27.miage.dto.ETicket;
import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.ExpiredTransitionalTicketException;
import fr.pantheonsorbonne.ufr27.miage.model.Ticket;
import fr.pantheonsorbonne.ufr27.miage.service.TicketingService;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class TicketGateway {

    @Inject
    TicketingService ticketingService;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    CamelContext camelContext;

    @Handler
    void emitTicket(ETicket eticket) throws NoSuchTicketException, CustomerNotFoundException.NoSeatAvailableException, ExpiredTransitionalTicketException {
        try {
            ticketingService.emitTicket(eticket);
        } catch (ExpiredTransitionalTicketException e) {
            ticketingService.cleanUpTransitionalTicket(eticket.getTransitionalTicketId());
            throw e;
        }
    }


    public void cancelTicket(Ticket eticket) {
        try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {
            producerTemplate.sendBodyAndHeader("direct:ticketCancel", new CancelationNotice(eticket.getIdCustomer().getEmail(), eticket.getId()), "vendorId", eticket.getIdVendor().getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
