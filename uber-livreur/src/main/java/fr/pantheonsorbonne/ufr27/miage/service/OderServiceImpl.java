package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.OrderDAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@ApplicationScoped
public class OderServiceImpl implements OrderService{
    @Inject
    OrderDAO orderDAO;

    @Inject
    DkDAO dkDAO;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void createOrder(String dKname) {
       orderDAO.newOrder(dkDAO.findDKByName(dKname));
    }


}
