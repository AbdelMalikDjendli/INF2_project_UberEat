package fr.pantheonsorbonne.ufr27.miage.ressource;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

import java.util.List;


@Path("/barcode")
public class BarcodeRessource {

    ProducerTemplate producerTemplate;


    @GET
    @Path("/generate")
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateBarcode() {
        // Appelez votre route Camel pour générer le code-barres
        byte[] barcodeImage = producerTemplate.requestBody("direct:generateBarcode", null, byte[].class);

        // Retournez l'image du code-barres en tant que réponse HTTP
        return Response.ok(barcodeImage, "image/png").build();
    }

}
