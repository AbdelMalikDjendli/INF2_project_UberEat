package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Customer;
import fr.pantheonsorbonne.ufr27.miage.model.Ticket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TicketDAOImpl implements TicketDAO {


    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Ticket findTicket(int transitionalTicketId) throws NoSuchTicketException {
        Ticket t = em.find(Ticket.class, transitionalTicketId);
        if (null == t) {
            throw new NoSuchTicketException();
        }
        return t;
    }

    @Override
    @Transactional
    public Ticket emitTicketForCustomer(int transitionalTickerId, Customer customer) {
        Ticket t = em.find(Ticket.class, transitionalTickerId);
        t.setIdCustomer(customer);
        return t;

    }

    @Override
    @Transactional
    public void removeTransitionalTicket(int transitionalTicketId) {
        Ticket t = em.find(Ticket.class, transitionalTicketId);
        if (t != null) {
            em.remove(t);
        }
    }


}
