package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderDAOImpl implements OrderDAO {
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;

    @Override
    @Transactional
    public Order findOrderById(long id) {
        return em.find(Order.class, id);
    }

    @Override
    @Transactional
    public void updateStatus(long orderId, String status) {
        Order o = em.find(Order.class, orderId);
        o.setStatus(status);
    }

    @Override
    @Transactional
    public void addDarkKitchen(long orderId, DarkKitchen dk) {
        Order o = em.find(Order.class, orderId);
        o.setDarkKitchen(dk);
    }

    @Override
    @Transactional
    public Order getLastOrder() {
        return (Order) this.em.createQuery("SELECT o FROM Order o ORDER BY o.id DESC", Order.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
