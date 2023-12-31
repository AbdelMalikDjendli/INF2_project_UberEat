package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import org.apache.camel.Processor;

public interface OrderDAO {
    void updateStatus(long orderId, String status);
    void addDarkKitchen(long orderId, DarkKitchen dk);
    Order findOrderById(long orderId);
    Order getLastOrder();

}
