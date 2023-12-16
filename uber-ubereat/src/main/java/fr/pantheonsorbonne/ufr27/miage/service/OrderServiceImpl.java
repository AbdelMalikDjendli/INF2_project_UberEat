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
        newOrder.setStatus(dtoOrder.status());

        // Récupérer et associer l'entité Menu à l'Order
        Menu menu = entityManager.find(Menu.class, (long) dtoOrder.menu_id()); // Assurez-vous que la conversion de type est correcte
        newOrder.setMenu(menu);

        entityManager.persist(newOrder);
        return newOrder;
    }
}
