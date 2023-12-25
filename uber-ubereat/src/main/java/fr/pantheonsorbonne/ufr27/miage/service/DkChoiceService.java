package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;

public interface DkChoiceService {
    public int getMinEstimation();
    public void setMinEstimation(int estimation);

    void setNumberOfEstimation();

    public void setDkName(String name);

    public String getDkName();

    int getNumberOfEstimation();

    void resetEstimations();


}
