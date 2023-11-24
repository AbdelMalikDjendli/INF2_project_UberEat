package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Customer;
import fr.pantheonsorbonne.ufr27.miage.model.Ticket;

public interface TicketDAO {
    Ticket findTicket(int transitionalTicketId) throws NoSuchTicketException;

    Ticket emitTicketForCustomer(int transitionalTickerId, Customer customer);

    void removeTransitionalTicket(int transitionalTicketId);
}
