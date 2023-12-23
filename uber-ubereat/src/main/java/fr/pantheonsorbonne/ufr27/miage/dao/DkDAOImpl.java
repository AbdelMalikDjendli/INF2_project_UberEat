package fr.pantheonsorbonne.ufr27.miage.dao;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DkDAOImpl implements DkDAO {
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;

    public DkDAOImpl() {
    }
@Override
    @Transactional
    public DarkKitchen findDKByName(String name) {
        return (DarkKitchen) this.em.createQuery("Select d from DarkKitchen d where d.name =: name").setParameter("name",name).getSingleResult();

    }

}



