package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;

public interface DeliveryManDAO {
    String getDeliveryManName();

    Boolean isDeliveryManAvaible();

    String getDeliveryManVehicule();

    long getIdDeliveryMan();

    void setDeliveryManIsAvaible(long id,boolean b);
}
