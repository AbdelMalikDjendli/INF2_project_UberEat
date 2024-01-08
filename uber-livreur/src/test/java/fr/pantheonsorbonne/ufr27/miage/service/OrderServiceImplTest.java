package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import jakarta.enterprise.context.ApplicationScoped;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ApplicationScoped
public class OrderServiceImplTest {

    @InjectMocks
    private OderServiceImpl orderService;

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private DkDAO dkDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        String dkName = "DKTest";

        // Créer un objet simulé de DarkKitchen
        DarkKitchen dk = new DarkKitchen();
        dk.setId(1L);
        dk.setName(dkName);

        // Définir le comportement simulé du mock DkDAO
        when(dkDAO.findDKByName(dkName)).thenReturn(dk);

        // Appeler la méthode à tester dans OrderServiceImpl
        orderService.createOrder(dkName);

        // Vérifier que la méthode newOrder du mock OrderDAO a été appelée avec le résultat de findDKByName
        verify(orderDAO).newOrder(dk);
    }

}
