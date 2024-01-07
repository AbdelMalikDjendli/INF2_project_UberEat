package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import fr.pantheonsorbonne.ufr27.miage.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private MenuDAO menuDAO;

    @Mock
    private OrderDAO orderDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        // Créez un menu simulé
        Menu mockMenu = new Menu();
        mockMenu.setId(1L);
        mockMenu.setName("Pizza");
        mockMenu.setDescription("Delicious pizza");

        // Définissez le comportement simulé du mock MenuDAO
        when(menuDAO.findMenuByName("Pizza")).thenReturn(mockMenu);

        // Testez la méthode createOrder
        orderService.createOrder("Pizza");

        // Vérifiez que la méthode newOrder du mock OrderDAO a été appelée avec le menu simulé
        verify(orderDAO).newOrder(mockMenu);
    }
}

