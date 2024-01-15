package fr.pantheonsorbonne.ufr27.miage.service;
import fr.pantheonsorbonne.ufr27.miage.dao.DeliveryManDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class DeliveryManServiceImplTest {

    @InjectMocks
    private DeliveryManServiceImpl deliveryManService;

    @Mock
    private DeliveryManDAO deliveryManDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsDeliveryManAvailable() {

        when(deliveryManDAO.isDeliveryManAvaible()).thenReturn(true);
        boolean result = deliveryManService.isDeliveryManAvailable();

        assertTrue(result);
    }

    @Test
    void testGetVehiculeDeliveryMan() {

        when(deliveryManDAO.getDeliveryManVehicule()).thenReturn("Car");
        String result = deliveryManService.getVehiculeDeliveryMan();
        assertEquals("Car", result);
    }

}
