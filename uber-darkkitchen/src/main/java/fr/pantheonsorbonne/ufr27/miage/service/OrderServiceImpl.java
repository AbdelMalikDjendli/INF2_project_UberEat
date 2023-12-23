package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {
    @Inject
    MenuDAO menuDAO;

    @Inject
    OrderDAO orderDAO;

    @Override
    @Transactional
    public void createOrder(String menuName) {
        orderDAO.newOrder(menuDAO.findMenuByName(menuName));
    }
}
