package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;
import fr.pantheonsorbonne.ufr27.miage.model.Order;

public interface OrderDAO {
    void updateStatus(long orderId, String status);
    void addDarkKitchen(long orderId, DarkKitchen dk);

    void addDeliveryMan(long orderId, DeliveryMan deliveryMan);
    Order findOrderById(long id);

    Order getLastOrder();



}
