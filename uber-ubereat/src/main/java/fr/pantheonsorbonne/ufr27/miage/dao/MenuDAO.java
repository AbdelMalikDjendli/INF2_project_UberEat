package fr.pantheonsorbonne.ufr27.miage.dao;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import fr.pantheonsorbonne.ufr27.miage.model.Menu;

import java.util.List;

public interface MenuDAO {

    List<Menu> getAllMenus();
    Menu findMenuByName(String name);
}


