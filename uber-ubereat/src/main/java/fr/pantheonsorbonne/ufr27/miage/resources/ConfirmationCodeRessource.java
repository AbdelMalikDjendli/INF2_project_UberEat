package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.service.ConfirmationCodeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/code")
public class ConfirmationCodeRessource {

    @Inject
    ConfirmationCodeService confirmationCodeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNewCode() {
        return Response.ok(confirmationCodeService.generateCode()).build();
    }
}
