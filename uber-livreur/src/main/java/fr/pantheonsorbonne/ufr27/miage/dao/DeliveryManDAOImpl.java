package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeliveryManDAOImpl implements DeliveryManDAO {

    @PersistenceContext(name = "mysql")
    EntityManager em;

    @Override
    @Transactional
    public String getDeliveryManName() {
        return (String) this.em.createQuery("Select d.name from DeliveryMan d").getSingleResult();

    }

    @Override
    @Transactional
    public Boolean isDeliveryManAvaible() {
        return (Boolean) this.em.createQuery("Select d.isAvailable from DeliveryMan d").getSingleResult();

    }

    @Override
    @Transactional
    public String getDeliveryManVehicule() {
        return (String) this.em.createQuery("Select d.vehicleType from DeliveryMan d").getSingleResult();
    }

    @Override
    @Transactional
    public long getIdDeliveryMan() {
        return (long) this.em.createQuery("Select d.id from DeliveryMan d").getSingleResult();
    }
}
