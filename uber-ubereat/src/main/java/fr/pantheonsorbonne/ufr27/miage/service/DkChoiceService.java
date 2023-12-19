package fr.pantheonsorbonne.ufr27.miage.service;

public interface DkChoiceService {
    public int getMinEstimation(int estimation);
    public void setMinEstimation(int estimation);

    public void setCurrentDarkKitchen(String name);

    public String getCurrentDarkKitchen();

}
