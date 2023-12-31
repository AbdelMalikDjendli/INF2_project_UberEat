package fr.pantheonsorbonne.ufr27.miage.dao;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MenuDAOImpl implements MenuDAO {
    @PersistenceContext(
            name = "mysql"
    )
    EntityManager em;


    @Override
    @Transactional
    public Menu findMenuByName(String name) {
        return (Menu) this.em.createQuery("Select m from Menu m where m.name =: name").setParameter("name", name).getSingleResult();

    }

    @Override
    @Transactional
    public List<Menu> getAllMenus() {
        return em.createQuery("SELECT m FROM Menu m", Menu.class).getResultList();
    }
}

