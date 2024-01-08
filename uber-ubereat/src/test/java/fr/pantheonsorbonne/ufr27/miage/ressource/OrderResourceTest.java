package fr.pantheonsorbonne.ufr27.miage.ressource;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.pantheonsorbonne.ufr27.miage.camel.OrderGateway;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import fr.pantheonsorbonne.ufr27.miage.resources.OrderResource;
import fr.pantheonsorbonne.ufr27.miage.service.OrderService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderResourceTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderGateway orderGateway;

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private OrderResource orderResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() throws JsonProcessingException {
        MenuDTO menuDTO = new MenuDTO("Pizza", "Delicious pizza");
        OrderDTO orderDTO = new OrderDTO(menuDTO, "DKName");
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderService.createOrder(orderDTO)).thenReturn(mockOrder);

        Response response = orderResource.createOrder(orderDTO);

        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(UriBuilder.fromResource(OrderResource.class)
                .path(OrderResource.class, "getOrderStatus")
                .build(mockOrder.getId()).toString(), response.getLocation().toString());
        verify(orderGateway).sendOrderToDarkkitchen(mockOrder.getId());
    }

    @Test
    void testGetOrderStatus() {
        Long orderId = 1L;
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setStatus("En cours de préparation");
        when(orderDAO.findOrderById(orderId)).thenReturn(mockOrder);

        Response response = orderResource.getOrderStatus(orderId);

        assertNotNull(response);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("En cours de préparation", response.getEntity().toString());
    }
}
