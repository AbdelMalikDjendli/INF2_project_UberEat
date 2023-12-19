package fr.pantheonsorbonne.ufr27.miage.dao;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class DkDAOImpl implements DkDAO {
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;

    public DkDAOImpl() {
    }

    public String getDKName() {
        return (String) this.em.createQuery("Select d.name from DarkKitchen d").getSingleResult();
    }
}


