package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAllMenus();

    Menu getMenuById(Long id);
}
