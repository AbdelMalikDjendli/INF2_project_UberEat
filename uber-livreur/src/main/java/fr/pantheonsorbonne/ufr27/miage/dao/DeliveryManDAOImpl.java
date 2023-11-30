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
    public DeliveryMan createNewDeliveryMan(String name, String vehicleType, double latPosition, double lonPosition){
        DeliveryMan d = new DeliveryMan();
        d.setName(name);
        d.setVehicleType(vehicleType);
        d.setLatPosition(latPosition);
        d.setLonPosition(lonPosition);
        em.persist(d);
        return d;
    }
}
