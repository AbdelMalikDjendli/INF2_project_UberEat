package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;

public interface DeliveryManDAO {
    DeliveryMan findDMById(Long id);

    void setIsAvaible(long id,boolean b);
}
