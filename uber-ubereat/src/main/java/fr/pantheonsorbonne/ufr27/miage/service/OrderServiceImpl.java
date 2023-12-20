package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import io.quarkus.logging.Log;
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
        Log.info("Début de la création de la commande avec le menu ID: " + dtoOrder.menu_id());
        Order newOrder = new Order();

        // Récupérer et associer l'entité Menu à l'Order
        Menu menu = entityManager.find(Menu.class, (long) dtoOrder.menu_id());
        newOrder.setMenu(menu);

        // Le statut est déjà initialisé par défaut dans l'entité Order
        entityManager.persist(newOrder);
        return newOrder;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return entityManager.find(Order.class, orderId);
    }
}
