package fr.pantheonsorbonne.ufr27.miage.dao;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DkDAOImpl implements DkDAO {
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;

    @Transactional
    public String getDKName() {
        return (String) this.em.createQuery("Select d.name from DarkKitchen d").getSingleResult();
    }
}


