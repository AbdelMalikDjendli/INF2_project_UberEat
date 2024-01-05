package fr.pantheonsorbonne.ufr27.miage.service;

public interface DeliveryManService {
    boolean isDeliveryManAvailable();

    String getVehiculeDeliveryMan();

    long getIdDeliveryMan();

    void setDeliveryManIsAvaible(long id,boolean b);
    String getNameDeliveryMan();


}
