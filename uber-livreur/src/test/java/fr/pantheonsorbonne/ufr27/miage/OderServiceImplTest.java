package fr.pantheonsorbonne.ufr27.miage;

import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.service.OderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class OderServiceImplTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private DkDAO dkDAO;

    @InjectMocks
    private OderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder() {
        String dkName = "DKName";
        DarkKitchen mockDk = new DarkKitchen();
        mockDk.setName(dkName);

        when(dkDAO.findDKByName(dkName)).thenReturn(mockDk);

        orderService.createOrder(dkName);

        verify(dkDAO).findDKByName(dkName);
        verify(orderDAO).newOrder(mockDk);
    }
}
