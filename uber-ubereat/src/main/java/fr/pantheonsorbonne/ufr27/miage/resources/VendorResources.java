package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.service.VenueService;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/vendor")
public class VendorResources {

    @Inject
    protected VenueService service;

    @Path("{idVendor}/venue/{idVenue}/quota")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public RemainingQuota getQuotas(@PathParam("idVendor") int idVendor, @PathParam("idVenue") int idVenue) {
        return service.getRemainingQuotaForVendor(idVendor, idVenue);
    }

    @Path("{idVendor}/venues")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<Gig> getGigs(@PathParam("idVendor") int idVendor) {
        return service.getAvailableGigs(idVendor);
    }
}