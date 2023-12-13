package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MenuServiceImpl implements MenuService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Menu> getAllMenus() {
        return em.createQuery("SELECT m FROM Menu m", Menu.class).getResultList();
    }

    @Override
    @Transactional
    public Menu getMenuById(Long id) {
        return em.find(Menu.class, id);
    }
}
