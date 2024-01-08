package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.*;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private MenuDAO menuDAO;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private DkDAO dkDAO;

    @Mock
    private DeliveryManDAO deliveryManDAO;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateOrder() {
        OrderDTO dtoOrder = new OrderDTO(new MenuDTO("Pizza", "Delicious pizza"), "DKName");
        Menu mockMenu = new Menu();
        mockMenu.setName("Pizza");
        when(menuDAO.findMenuByName("Pizza")).thenReturn(mockMenu);

        Order result = orderService.createOrder(dtoOrder);

        assertNotNull(result);
        assertEquals(mockMenu, result.getMenu());
        assertEquals("En recherche de restaurant", result.getStatus());
        verify(entityManager).persist(any(Order.class));
    }

    @Test
    void testGetOrderDTOFromModel() {
        long orderId = 1L;
        Order mockOrder = new Order();
        Menu mockMenu = new Menu();
        mockMenu.setName("Pizza");
        mockMenu.setDescription("Delicious pizza");
        mockOrder.setMenu(mockMenu);
        when(orderDAO.findOrderById(orderId)).thenReturn(mockOrder);

        OrderDTO result = orderService.getOrderDTOFromModel(orderId);

        assertNotNull(result);
        assertEquals("Pizza", result.menu().name());
        assertEquals("Delicious pizza", result.menu().description());
    }

    @Test
    void testDkFoundUpdate() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderDAO.getLastOrder()).thenReturn(mockOrder);

        DarkKitchen mockDk = new DarkKitchen();
        mockDk.setName("DKName");
        when(dkDAO.findDKByName("DKName")).thenReturn(mockDk);

        Order result = orderService.dkFoundUpdate("DKName");

        assertNotNull(result);
        verify(orderDAO).updateStatus(1L, "en cours de préparation");
        verify(orderDAO).addDarkKitchen(1L, mockDk);
    }

    @Test
    void testDeliveryManUpdate() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderDAO.getLastOrder()).thenReturn(mockOrder);

        DeliveryMan mockDm = new DeliveryMan();
        mockDm.setName("John Doe");
        when(deliveryManDAO.findDMByName("John Doe")).thenReturn(mockDm);

        Order result = orderService.deliveryManUpdate("John Doe");

        assertNotNull(result);
        verify(orderDAO).updateStatus(1L, "livreur trouvé");
        verify(orderDAO).addDeliveryMan(1L, mockDm);
        verify(deliveryManDAO).setIsAvaible("John Doe", false);
    }

    @Test
    void testNoneDeliveryManUpdate() {
        long orderId = 1L;
        orderService.noneDeliveryManUpdate(orderId);
        verify(orderDAO).updateStatus(orderId, "aucun livreur trouvé");
    }

    @Test
    void testCountTotalDeliveryMen() {
        when(deliveryManDAO.countTotalDeliveryMen()).thenReturn(5);
        int count = orderService.countTotalDeliveryMen();
        assertEquals(5, count);
    }

    @Test
    void testUpdateOrderStatus() {
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderDAO.getLastOrder()).thenReturn(mockOrder);
        orderService.updateOrderStatus("en livraison");
        verify(orderDAO).updateStatus(1L, "en livraison");
    }

    @Test
    void testGetCurrentOrder() {
        Order mockOrder = new Order();
        when(orderDAO.getLastOrder()).thenReturn(mockOrder);
        Order result = orderService.getCurrentOrder();
        assertEquals(mockOrder, result);
    }
}

