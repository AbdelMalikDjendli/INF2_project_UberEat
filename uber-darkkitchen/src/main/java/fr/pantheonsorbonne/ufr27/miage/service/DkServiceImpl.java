package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.DkDAO;
import fr.pantheonsorbonne.ufr27.miage.model.DarkKitchen;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DkServiceImpl implements DkService{
    @Inject
    DkDAO dkDAO;

    @Override
    public String getCurrentDkName(){
        return  dkDAO.getDKName();
    }
}
