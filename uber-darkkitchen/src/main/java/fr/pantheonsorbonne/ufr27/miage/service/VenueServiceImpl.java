package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.camel.TicketGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.VenueQuotaDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Gig;
import fr.pantheonsorbonne.ufr27.miage.dto.RemainingQuota;
import fr.pantheonsorbonne.ufr27.miage.model.Ticket;
import fr.pantheonsorbonne.ufr27.miage.model.Venue;
import fr.pantheonsorbonne.ufr27.miage.model.VenueLineUp;
import fr.pantheonsorbonne.ufr27.miage.model.VenueQuota;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RequestScoped
public class VenueServiceImpl implements VenueService {

    @PersistenceContext
    EntityManager em;

    @Inject
    VenueQuotaDAO venueQuotaDAO;

    @Inject
    TicketGateway ticketGateway;


    @Override
    public RemainingQuota getRemainingQuotaForVendor(int idVendor, int idVenue) {
        VenueQuota quota = venueQuotaDAO.getQuotaForVendorForVenue(idVendor, idVenue);
        return new RemainingQuota(quota.getStandingQuota(), quota.getSeatingQuota());


    }

    @Override
    public Collection<Gig> getAvailableGigs(int idVendor) {
        Collection<Gig> gigs = new LinkedList<>();
        for (Venue venue : venueQuotaDAO.getQuotaForVendor(idVendor)) {
            StringBuilder builder = new StringBuilder();
            for (VenueLineUp lineUp : venue.getLineUp()) {
                builder.append(lineUp.getId().getArtist().getName());
                builder.append(" and ");
            }
            String allArtists = builder.substring(0, builder.length() - 5);
            gigs.add(new Gig(allArtists, venue.getLocation().getName(), venue.getVenueDate(), venue.getId().intValue()));
        }

        return gigs;
    }

    private void removeArtistFromVenue(int artistId, int venueId) {

    }

    @Transactional
    @Override
    public void cancelVenueForArtist(int artistId, int venueId) {


        Venue venue = em.find(Venue.class, venueId);

        Collection<VenueLineUp> venueLineupToRemove = removeVenuesLineup(artistId, venue);

        cancelTicketsWithEmptyLineups(venueId, venueLineupToRemove);


    }


    protected void cancelTicketsWithEmptyLineups(int venueId, Collection<VenueLineUp> venueLineupToRemove) {
        if (!venueLineupToRemove.isEmpty()) {
            List<Ticket> ticketsToCancel = em.createQuery("SELECT  t from Ticket t where t.idVenue.id=:venueId").setParameter("venueId", venueId).getResultList();
            for (Ticket t : ticketsToCancel) {
                ticketGateway.cancelTicket(t);
                em.remove(t);
            }


        }
    }


    protected Collection<VenueLineUp> removeVenuesLineup(int artistId, Venue venue) {
        Collection<VenueLineUp> venueLineupToRemove = new ArrayList<>();
        for (VenueLineUp lineup : venue.getLineUp()) {
            if (lineup.getId().getArtist().getIdArtist().equals(artistId)) {
                venueLineupToRemove.add(lineup);
            }
        }

        for (VenueLineUp lu : venueLineupToRemove) {
            em.remove(lu);
        }

        return venueLineupToRemove;
    }
}
