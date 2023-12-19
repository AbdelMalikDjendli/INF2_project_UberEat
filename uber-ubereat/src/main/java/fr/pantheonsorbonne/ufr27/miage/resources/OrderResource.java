package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.camel.OrderGateway;
import fr.pantheonsorbonne.ufr27.miage.dto.Order;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(Order orderDTO) {
        fr.pantheonsorbonne.ufr27.miage.model.Order createdOrder = orderService.createOrder(orderDTO);

        // Construire l'URL pour accéder à la commande créée
        URI orderUri = UriBuilder.fromResource(OrderResource.class)
                .path(OrderResource.class, "getOrderStatus")
                .build(createdOrder.getId());

        orderGateway.sendMenuToDarkkitchen(orderDTO.menu_id());

        // Renvoyer le code 201 avec l'URL de la commande dans l'en-tête "Location"
        return Response.created(orderUri)
                .entity(createdOrder) // Optionnel, si vous souhaitez renvoyer les détails de la commande
                .build();
    }

    @Path("/{orderId}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getOrderStatus(@PathParam("orderId") Long orderId) {
        fr.pantheonsorbonne.ufr27.miage.model.Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return Response.ok(order.getStatus()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
