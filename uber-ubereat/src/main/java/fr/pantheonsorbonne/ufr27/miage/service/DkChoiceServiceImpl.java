package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DkChoiceServiceImpl implements DkChoiceService {

    private int currentEstimation = 100;
    private String dkName;
    private int numberOfEstimation = 0;

    @Override
    public void setMinEstimation(int estimation) {
        this.currentEstimation = estimation;
    }
    @Override
     public void setNumberOfEstimation(){
        this.numberOfEstimation++;
     }

    @Override
    public void setDkName(String name) {
        this.dkName = name;
    }

    @Override
    public int getMinEstimation() {
        return this.currentEstimation;
    }

    @Override
    public int getNumberOfEstimation() {
        return this.numberOfEstimation;
    }

    @Override
    public String getDkName() {
        return this.dkName;
    }

    @Override
    public void resetEstimations() {
        this.numberOfEstimation = 0;
        this.currentEstimation = 100;
    }

}
