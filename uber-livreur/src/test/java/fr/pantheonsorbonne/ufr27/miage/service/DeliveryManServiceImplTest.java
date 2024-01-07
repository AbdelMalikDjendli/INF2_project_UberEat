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
        // Définissez le comportement simulé du mock DeliveryManDAO
        when(deliveryManDAO.isDeliveryManAvaible()).thenReturn(true);

        // Testez la méthode isDeliveryManAvailable de DeliveryManServiceImpl
        boolean result = deliveryManService.isDeliveryManAvailable();

        // Vérifiez que la méthode isDeliveryManAvaible du mock DeliveryManDAO a été appelée
        // Vérifiez que le résultat est vrai (true)
        assertTrue(result);
    }

    @Test
    void testGetVehiculeDeliveryMan() {
        // Définissez le comportement simulé du mock DeliveryManDAO
        when(deliveryManDAO.getDeliveryManVehicule()).thenReturn("Car");

        // Testez la méthode getVehiculeDeliveryMan de DeliveryManServiceImpl
        String result = deliveryManService.getVehiculeDeliveryMan();

        // Vérifiez que la méthode getDeliveryManVehicule du mock DeliveryManDAO a été appelée
        // Vérifiez que le résultat est "Car"
        assertEquals("Car", result);
    }

}
