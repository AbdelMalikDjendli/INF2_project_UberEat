package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.DeliveryMan;
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
    public DeliveryMan findDMById(Long id) {
        return (DeliveryMan) this.em.createQuery("Select d from DeliveryMan d where d.id =: id").setParameter("id", id).getSingleResult();

    }
}
