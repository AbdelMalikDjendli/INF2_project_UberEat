package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;

public interface DkDAO {
    DarkKitchen findDKByName(String name);
}
