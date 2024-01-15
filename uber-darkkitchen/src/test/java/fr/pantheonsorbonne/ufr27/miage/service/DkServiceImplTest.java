package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.service.DkServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class DkServiceImplTest {

    @InjectMocks
    private DkServiceImpl dkService;

    @Mock
    private DkDAO dkDAO;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCurrentDkName() {

        when(dkDAO.getDKName()).thenReturn("DKTest");


        String result = dkService.getCurrentDkName();


        Assertions.assertEquals("DKTest", result);
    }
}
