package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;

public interface DkChoiceService {
    public int getMinEstimation();
    public void setMinEstimation(int estimation);

    void setNumberOfEstimation();

    public void setDkQueue(String name);

    public String getDkQueue();

    int getNumberOfEstimation();

}
