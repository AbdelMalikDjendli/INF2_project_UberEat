package fr.pantheonsorbonne.ufr27.miage.service;

public interface DeliveryManService {
    void setDeliveryManStatus(String name,boolean isAvailable);

    boolean isGoodDm(String dmName);
}
