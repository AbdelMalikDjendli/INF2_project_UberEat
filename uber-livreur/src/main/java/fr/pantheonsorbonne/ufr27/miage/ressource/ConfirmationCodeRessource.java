package fr.pantheonsorbonne.ufr27.miage.ressource;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.pantheonsorbonne.ufr27.miage.camel.OrderGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.CodeDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import fr.pantheonsorbonne.ufr27.miage.service.ConfirmationCodeService;
import fr.pantheonsorbonne.ufr27.miage.service.DeliveryManService;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;
import java.util.ArrayList;

@Path("code")
public class ConfirmationCodeRessource {

    @Inject
    OrderService orderService;

    @Inject
    OrderGateway orderGateway;

    @Inject
    ConfirmationCodeService confirmationCodeService;

    @Inject
    OrderDAO orderDAO;

    @Inject
    DeliveryManService deliveryManService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmDelivery(CodeDTO codeDTO) throws JsonProcessingException, InterruptedException {

        orderGateway.sendConfirmationCode(codeDTO.value());

        while (confirmationCodeService.isGoodCode()==null) {
            Thread.sleep(100);
        }

        if(confirmationCodeService.isGoodCode().equals("true")){
            confirmationCodeService.setGoodCode(null);
            deliveryManService.setDeliveryManIsAvaible(deliveryManService.getIdDeliveryMan(),true);
            return Response.accepted().build();
        }
        else if (confirmationCodeService.isGoodCode().equals("false")){
            confirmationCodeService.setGoodCode(null);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else{
            deliveryManService.setDeliveryManIsAvaible(deliveryManService.getIdDeliveryMan(),true);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}