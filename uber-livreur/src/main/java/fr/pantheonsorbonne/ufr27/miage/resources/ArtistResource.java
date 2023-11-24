package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.service.VenueService;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("artist")
public class ArtistResource {

    @Inject
    VenueService venueService;

    @Path("{artistId}/venue/{venueId}")
    @DELETE
    public void cancelVenue(@PathParam("artistId") int artistId, @PathParam("venueId") int venueId) {
        venueService.cancelVenueForArtist(artistId, venueId);
    }
}
