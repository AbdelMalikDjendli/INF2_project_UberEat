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
public class MenuDAOImpl implements MenuDAO {
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;

    public MenuDAOImpl() {
    }

    public Menu findMenuById(int id) {
        return (Menu) this.em.createQuery("Select m from Menu m where m.id =: id").setParameter("id",id).getSingleResult();
    }
}

