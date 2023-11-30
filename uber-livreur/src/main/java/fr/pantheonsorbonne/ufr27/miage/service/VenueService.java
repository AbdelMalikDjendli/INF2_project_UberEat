package fr.pantheonsorbonne.ufr27.miage.service;



import java.util.Collection;

public interface VenueService {
    RemainingQuota getRemainingQuotaForVendor(int idVendor, int idVenue);

    Collection<Gig> getAvailableGigs(int idVendor);

    void cancelVenueForArtist(int artistId, int venueId);
}
