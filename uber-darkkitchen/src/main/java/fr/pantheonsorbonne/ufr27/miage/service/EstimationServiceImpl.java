package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Random;

public class EstimationServiceImpl implements EstimationService {

    @Override
    public String getRandomEstimation() {
        Random random = new Random();
        int randomNumber = random.nextInt(30);
        return Integer.toString(randomNumber);
    }
}
