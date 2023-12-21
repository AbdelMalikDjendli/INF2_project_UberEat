package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.MenuDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Body;
import org.hibernate.service.spi.InjectService;

@ApplicationScoped
public class MenuServiceImpl implements MenuService {
    @Inject
    private EstimationService estimationService;
    @Inject
    private MenuDAO menuDAO;
    @Override
    public Boolean isPrepared(MenuDto menu) {
        return null;
    }

    @Override
    public Boolean menuExists(MenuDto menu) {
        return null;
    }
}
