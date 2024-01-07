package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
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
    public void newOrder(Menu menu) {
        Order order = new Order();
        order.setMenu(menu);
        em.persist(order);
    }
}
