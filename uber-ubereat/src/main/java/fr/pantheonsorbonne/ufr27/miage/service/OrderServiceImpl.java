package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderServiceImpl implements OrderService{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public Order createOrder(fr.pantheonsorbonne.ufr27.miage.dto.Order order) {
        Order newOrder = new Order();
        newOrder.setStatus(order.status());
        entityManager.persist(order);
        return newOrder;
    }
}
