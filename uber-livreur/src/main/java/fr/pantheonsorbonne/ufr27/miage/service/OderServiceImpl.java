package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.OrderDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OderServiceImpl implements OrderService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createOrder(String name) {
        Order newOrder = new Order();
        // Récupérer et associer l'entité Menu à l'Order


    }
}
