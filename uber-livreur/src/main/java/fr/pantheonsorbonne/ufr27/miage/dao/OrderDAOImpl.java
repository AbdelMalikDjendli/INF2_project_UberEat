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
    public void newOrder(DarkKitchen dk) {
        Order order = new Order();
        order.setDk(dk);
        em.persist(order);
    }

    @Override
    @Transactional
    public int countTotalOrder() {
        Long count = em.createQuery("SELECT COUNT(d) FROM Order d", Long.class)
                .getSingleResult();
        return count.intValue();
    }



}
