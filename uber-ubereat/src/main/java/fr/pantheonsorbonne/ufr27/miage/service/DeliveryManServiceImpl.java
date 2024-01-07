package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DeliveryManDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DeliveryManServiceImpl implements DeliveryManService {
    @Inject
    DeliveryManDAO deliveryManDAO;

    @Inject
    OrderDAO orderDAO;

    public void setDeliveryManStatus(String name, boolean isAvailable) {
        deliveryManDAO.setIsAvaible(name, isAvailable);
    }

    public boolean isGoodDm(String dmName) {
        return deliveryManDAO.findDMByName(dmName).getId() == orderDAO.getLastOrder().getDeliveryMan().getId();
    }
}
