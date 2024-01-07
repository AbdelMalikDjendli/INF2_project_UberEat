package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.service.EstimationServiceImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EstimationServiceImplTest {

    @Test
    void testGetRandomEstimation() {
        // Créez une instance de EstimationServiceImpl à tester
        EstimationServiceImpl estimationService = new EstimationServiceImpl();

        // Appelez la méthode à tester pour obtenir une estimation aléatoire
        String randomEstimation = estimationService.getRandomEstimation();

        // Vérifiez que la chaîne renvoyée est un nombre entre 0 et 29 inclus
        int parsedEstimation = Integer.parseInt(randomEstimation);
        assertTrue(parsedEstimation >= 0 && parsedEstimation <= 29);
    }
}

