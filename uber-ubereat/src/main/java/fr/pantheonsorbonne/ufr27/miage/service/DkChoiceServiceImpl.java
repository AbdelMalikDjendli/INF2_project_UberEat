package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DkChoiceServiceImpl implements DkChoiceService {

    private int currentEstimation = 100;
    private String currentDarkKitchen;

    @Override
    public void setMinEstimation(int estimation) {
        if(estimation<currentEstimation){
            this.currentEstimation = estimation;
        }
    }

    @Override
    public void setCurrentDarkKitchen(String name) {
        this.currentDarkKitchen = name;
    }

    @Override
    public int getMinEstimation(int estimation) {
        return this.currentEstimation;
    }

    @Override
    public String getCurrentDarkKitchen() {
        return this.currentDarkKitchen;
    }
}
