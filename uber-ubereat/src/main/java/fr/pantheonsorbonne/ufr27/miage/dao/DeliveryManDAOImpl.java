package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;
import fr.pantheonsorbonne.ufr27.miage.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeliveryManDAOImpl implements DeliveryManDAO {

    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;


    @Override
    @Transactional
    public DeliveryMan findDMByName(String name) {
        return (DeliveryMan) this.em.createQuery("Select d from DeliveryMan d where d.name=:name").setParameter("name", name).getSingleResult();

    }

    @Override
    @Transactional
    public void setIsAvaible(String deliveryMenName,boolean b) {
        DeliveryMan dm = findDMByName(deliveryMenName);
        dm.setIsAvailable(b);
    }
}
