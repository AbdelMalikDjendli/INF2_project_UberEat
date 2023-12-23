package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class OrderDAOImpl implements OrderDAO{
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;
    @Override
    @Transactional
    public Order findOrderById(long id) {
        return (Order) this.em.createQuery("Select o from Order o where o.id =: id").setParameter("id",id).getSingleResult();
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
    public Order getLastOrder(){
        return (Order) this.em.createQuery("Select o from Order o").getSingleResult();
    }
}
