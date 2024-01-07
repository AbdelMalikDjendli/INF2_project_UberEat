package fr.pantheonsorbonne.ufr27.miage.dao;


import fr.pantheonsorbonne.ufr27.miage.model.Menu;

import java.util.List;

public interface MenuDAO {
    List<Menu> getAllMenu();

    Menu findMenuByName(String name);
}

