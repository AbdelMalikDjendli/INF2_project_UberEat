package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;

@ApplicationScoped
public class EstimationServiceImpl implements EstimationService {

    @Override
    public String getRandomEstimation() {
        Random random = new Random();
        int randomNumber = random.nextInt(30);
        return Integer.toString(randomNumber);
    }
}
