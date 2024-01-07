package fr.pantheonsorbonne.ufr27.miage.dao;



public interface DeliveryManDAO {
    String getDeliveryManName();

    Boolean isDeliveryManAvaible();

    String getDeliveryManVehicule();

    long getIdDeliveryMan();

    void setDeliveryManIsAvaible(long id,boolean b);
}
