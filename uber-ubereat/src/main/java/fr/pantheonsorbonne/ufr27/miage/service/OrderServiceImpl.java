package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
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
    public Order createOrder(fr.pantheonsorbonne.ufr27.miage.dto.Order dtoOrder) {
        Order newOrder = new Order();

        // Récupérer et associer l'entité Menu à l'Order
        Menu menu = entityManager.find(Menu.class, (long) dtoOrder.menu_id());
        newOrder.setMenu(menu);

        // Le statut est déjà initialisé par défaut dans l'entité Order
        entityManager.persist(newOrder);
        return newOrder;
    }
}
