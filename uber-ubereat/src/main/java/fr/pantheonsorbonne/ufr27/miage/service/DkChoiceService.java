package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;

public interface DkChoiceService {
    public int getMinEstimation(int estimation);
    public void setMinEstimation(int estimation);

    public void setDkQueue(String name);

    public String getDkQueue();
    String chooseFastestDarkKitchen(List<String> estimations);

}
