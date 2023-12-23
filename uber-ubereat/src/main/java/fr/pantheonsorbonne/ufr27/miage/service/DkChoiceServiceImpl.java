package fr.pantheonsorbonne.ufr27.miage.service;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DkChoiceServiceImpl implements DkChoiceService {

    private int currentEstimation = 100;
    private String dkQueue;
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
    public void setDkQueue(String name) {
        this.dkQueue = name;
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
    public String getDkQueue() {
        return this.dkQueue;
    }

}
