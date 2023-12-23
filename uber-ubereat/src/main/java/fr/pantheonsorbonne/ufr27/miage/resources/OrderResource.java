package fr.pantheonsorbonne.ufr27.miage.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.pantheonsorbonne.ufr27.miage.camel.OrderGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.net.URI;

@Path("orders")
public class OrderResource {

    @Inject
    OrderService orderService;

    @Inject
    OrderGateway orderGateway;

    @Inject
    OrderDAO orderDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(fr.pantheonsorbonne.ufr27.miage.dto.Order orderDTO) throws JsonProcessingException {
        Log.info("Création d'une nouvelle commande avec le menu ID: " + orderDTO.menu_id());
        fr.pantheonsorbonne.ufr27.miage.model.Order createdOrder = orderService.createOrder(orderDTO);

        // Construire l'URL pour accéder à la commande créée
        URI orderUri = UriBuilder.fromResource(OrderResource.class)
                .path(OrderResource.class, "getOrderStatus")
                .build(createdOrder.getId());

        orderGateway.sendOrderToDarkkitchen(createdOrder.getId());

        // Renvoyer le code 201 avec l'URL de la commande dans l'en-tête "Location"
        return Response.created(orderUri)
                .entity(createdOrder) // Optionnel, si vous souhaitez renvoyer les détails de la commande
                .build();
    }

    @Path("/{orderId}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getOrderStatus(@PathParam("orderId") Long orderId) {
        Order order = orderDAO.findOrderById(orderId);
        if (order != null) {
            return Response.ok(order.getStatus()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
