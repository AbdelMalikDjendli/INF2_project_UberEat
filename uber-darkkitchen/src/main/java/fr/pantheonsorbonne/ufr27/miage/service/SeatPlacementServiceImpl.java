package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.exception.CustomerNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Venue;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class SeatPlacementServiceImpl implements SeatPlacementService {


    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public String bookSeat(int venueId) throws CustomerNotFoundException.NoSeatAvailableException {
        Venue venue = em.find(Venue.class, venueId);

        String prefix=venue.getLocation().getName().substring(0,1);
        List<String> ticketForVenue = em.createQuery("SELECT t.seatReference from Ticket t where t.idVenue.id=:venueId and t.seatReference is not null").setParameter("venueId", venueId).getResultList();
        for(int i=0;i<venue.getLocation().getStandingGauge();i++){
            String tentativeSeat = prefix + "-" + i;
            if(ticketForVenue.contains(tentativeSeat)){
                continue;
            }
            return tentativeSeat;

        }

        throw new CustomerNotFoundException.NoSeatAvailableException(venueId);
    }
}
