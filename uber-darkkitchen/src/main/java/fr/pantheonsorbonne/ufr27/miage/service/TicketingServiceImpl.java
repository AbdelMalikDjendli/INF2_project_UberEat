package fr.pantheonsorbonne.ufr27.miage.service;

import com.google.common.hash.Hashing;
import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.exception.ExpiredTransitionalTicketException;
import fr.pantheonsorbonne.ufr27.miage.dao.CustomerDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.NoSuchTicketException;
import fr.pantheonsorbonne.ufr27.miage.dao.TicketDAO;

import fr.pantheonsorbonne.ufr27.miage.model.Customer;
import fr.pantheonsorbonne.ufr27.miage.model.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Objects;

@ApplicationScoped
public class TicketingServiceImpl implements TicketingService {


    @Inject
    CustomerDAO customerDAO;

    @Inject
    TicketDAO ticketDAO;

    @Inject
    SeatPlacementService seatPlacementService;

    public String getKeyForTicket(Ticket ticket) {
        return Hashing.sha256().hashString(ticket.getId() + "" + ticket.getIdVenue().getId() + "" + ticket.getIdVendor().getId() + "MySuperSecret75013!", StandardCharsets.UTF_8).toString();
    }

    @Override
    @Transactional
    public String emitTicket(ETicket eticket) throws ExpiredTransitionalTicketException, NoSuchTicketException, CustomerNotFoundException.NoSeatAvailableException {

        Customer customer = null;
        try {
            customer = customerDAO.findMatchingCustomer(eticket.getEmail());
        } catch (CustomerNotFoundException e) {
            customer = customerDAO.createNewCustomer(eticket.getFname(), eticket.getLname(), eticket.getEmail());
        }

        Ticket ticket = ticketDAO.findTicket(eticket.getTransitionalTicketId());
        if (ticket.getValidUntil().isBefore(Instant.now())) {
            throw new ExpiredTransitionalTicketException(eticket.getTransitionalTicketId());
        }
        ticket = ticketDAO.emitTicketForCustomer(eticket.getTransitionalTicketId(), customer);
        ticket.setTicketKey(this.getKeyForTicket(ticket));
        if (Objects.equals(eticket.getType(),TicketType.SEATING)) {
            ticket.setSeatReference(seatPlacementService.bookSeat(ticket.getIdVenue().getId()));
        }
        return ticket.getTicketKey();


    }

    @Override
    @Transactional
    public void cleanUpTransitionalTicket(int transitionalTicketId) {
        ticketDAO.removeTransitionalTicket(transitionalTicketId);
    }

    @Override
    public boolean validateTicket(Ticket t) {
        return this.getKeyForTicket(t).equals(t.getTicketKey());
    }
}
