package fr.pantheonsorbonne.ufr27.miage.dao;


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
    public List<Menu> getAllMenu() {
        return this.em.createQuery("Select m from Menu m").getResultList();
    }

    @Override
    @Transactional
    public Menu findMenuByName(String name) {
        return (Menu) this.em.createQuery("Select m from Menu m where m.name =: name").setParameter("name", name).getSingleResult();

    }
}

