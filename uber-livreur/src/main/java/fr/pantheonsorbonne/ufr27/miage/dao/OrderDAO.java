package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.Order;


public interface OrderDAO {
    void newOrder(DarkKitchen dk);
    int countTotalOrder();

}
