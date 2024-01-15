package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.service.EstimationServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstimationServiceImplTest {

    @Test
    void testGetRandomEstimation() {

        EstimationServiceImpl estimationService = new EstimationServiceImpl();


        String randomEstimation = estimationService.getRandomEstimation();


        int parsedEstimation = Integer.parseInt(randomEstimation);
        assertTrue(parsedEstimation >= 0 && parsedEstimation <= 29);
    }
}

