package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DeliveryManDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryManServiceImplTest {

    @Mock
    private DeliveryManDAO deliveryManDAO;

    @Mock
    private OrderDAO orderDAO;

    @InjectMocks
    private DeliveryManServiceImpl deliveryManService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSetDeliveryManStatus() {
        String dmName = "John Doe";
        boolean isAvailable = true;

        deliveryManService.setDeliveryManStatus(dmName, isAvailable);

        verify(deliveryManDAO, times(1)).setIsAvaible(dmName, isAvailable);
    }

    @Test
    void testIsGoodDm() {
        String dmName = "John Doe";
        long dmId = 1L;
        DeliveryMan mockDeliveryMan = new DeliveryMan();
        mockDeliveryMan.setId((int) dmId);
        mockDeliveryMan.setName(dmName);

        Order mockOrder = new Order();
        mockOrder.setDeliveryMan(mockDeliveryMan);

        when(deliveryManDAO.findDMByName(dmName)).thenReturn(mockDeliveryMan);
        when(orderDAO.getLastOrder()).thenReturn(mockOrder);

        boolean result = deliveryManService.isGoodDm(dmName);

        assertTrue(result, "isGoodDm devrait retourner true si l'ID du livreur correspond à celui de la dernière commande.");
    }
}
