package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MenuServiceImpl implements MenuService {


    @Inject
    MenuDAO menuDAO;

    @Override
    @Transactional
    public boolean isMenuPreparedByDk(String menuName) {
        List<Menu> allMenu = menuDAO.getAllMenu();
        List<Menu> allMenuFiltered = allMenu.stream()
                .filter(menu -> menu.getName().equals(menuName)).toList();
        return !allMenuFiltered.isEmpty();
    }

}

