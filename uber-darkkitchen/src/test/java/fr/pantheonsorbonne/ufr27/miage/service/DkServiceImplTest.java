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
        // Définissez le comportement simulé du mock DkDAO
        when(dkDAO.getDKName()).thenReturn("DKTest");

        // Appelez la méthode à tester dans DkServiceImpl
        String result = dkService.getCurrentDkName();

        // Vérifiez le résultat
        Assertions.assertEquals("DKTest", result);
    }
}
