package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DeliveryManDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DeliveryManServiceImpl implements DeliveryManService {

    @Inject
    DeliveryManDAO deliveryManDAO;


    @Override
    public boolean isDeliveryManAvailable() {
        return deliveryManDAO.isDeliveryManAvaible();
    }

    @Override
    public String getVehiculeDeliveryMan() {
        return deliveryManDAO.getDeliveryManVehicule();
    }

    @Override
    public long getIdDeliveryMan() {
        return deliveryManDAO.getIdDeliveryMan();
    }

    @Override
    public void setDeliveryManIsAvaible(long id, boolean b) {
        deliveryManDAO.setDeliveryManIsAvaible(id, b);
    }

    @Override
    public String getNameDeliveryMan() {
        return deliveryManDAO.getDeliveryManName();
    }

}
