package fr.pantheonsorbonnne.ufr27.miage;

import fr.pantheonsorbonne.ufr27.miage.dao.*;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.*;
import fr.pantheonsorbonne.ufr27.miage.service.OrderServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceImplTest {

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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateOrder() {
        // Configuration du mock
        Menu mockMenu = new Menu();
        when(menuDAO.findMenuByName(anyString())).thenReturn(mockMenu);

        // Appel de la méthode à tester
        OrderDTO dtoOrder = new OrderDTO("En recherche de restaurant", new MenuDTO("Pizza", "Delicious pizza"), "John Doe");
        Order createdOrder = orderService.createOrder(dtoOrder);

        // Assertions
        assertNotNull(createdOrder);
        assertEquals(mockMenu, createdOrder.getMenu());
        verify(entityManager, times(1)).persist(any(Order.class));
    }

    @Test
    void testGetOrderDTOFromModel() {
        // Configurer les mocks
        Order mockOrder = new Order();
        Menu mockMenu = new Menu();
        mockMenu.setName("Pizza");
        mockMenu.setDescription("Delicious pizza");
        mockOrder.setMenu(mockMenu);

        when(orderDAO.findOrderById(anyLong())).thenReturn(mockOrder);

        // Appel de la méthode à tester
        OrderDTO result = orderService.getOrderDTOFromModel(1L);

        // Assertions
        assertNotNull(result);
        assertEquals("Pizza", result.menu().name());
        assertEquals("Delicious pizza", result.menu().description());
    }

    @Test
    void testDkFoundUpdate() {
        // Configurer les mocks
        Order mockOrder = new Order();
        mockOrder.setId(1L);
        when(orderDAO.getLastOrder()).thenReturn(mockOrder);
        when(dkDAO.findDKByName(anyString())).thenReturn(new DarkKitchen());

        // Appel de la méthode à tester
        Order result = orderService.dkFoundUpdate("DK Name");

        // Assertions
        assertNotNull(result);
        verify(orderDAO, times(1)).updateStatus(anyLong(), eq("en cours de préparation"));
        verify(orderDAO, times(1)).addDarkKitchen(anyLong(), any(DarkKitchen.class));
    }

    @Test
    void testDeliveryManUpdate() {
        // Configurer les mocks
        Order mockOrder = new Order();
        mockOrder.setId(1L); // Assurez-vous que l'ID est défini

        when(orderDAO.getLastOrder()).thenReturn(mockOrder);
        when(deliveryManDAO.findDMByName(anyString())).thenReturn(new DeliveryMan());

        // Appel de la méthode à tester
        Order result = orderService.deliveryManUpdate("DM Name");

        // Assertions
        assertNotNull(result);
        verify(orderDAO, times(1)).updateStatus(anyLong(), eq("livreur trouvé"));
        verify(orderDAO, times(1)).addDeliveryMan(anyLong(), any(DeliveryMan.class));
        verify(deliveryManDAO, times(1)).setIsAvaible(anyString(), eq(false));
    }

    @Test
    void testNoneDeliveryManUpdate() {
        // Configurer les mocks
        Order mockOrder = new Order();
        when(orderDAO.findOrderById(anyLong())).thenReturn(mockOrder);

        // Appel de la méthode à tester
        orderService.noneDeliveryManUpdate(1L);

        // Assertions
        verify(orderDAO, times(1)).updateStatus(anyLong(), eq("aucun livreur trouvé"));
    }

    @Test
    void testCountTotalDeliveryMen() {
        // Configurer les mocks
        when(deliveryManDAO.countTotalDeliveryMen()).thenReturn(5);

        // Appel de la méthode à tester
        int count = orderService.countTotalDeliveryMen();

        // Assertions
        assertEquals(5, count);
    }
}
