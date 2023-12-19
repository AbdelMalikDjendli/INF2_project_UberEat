package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Body;

@ApplicationScoped
public class MenuServiceImpl implements MenuService {

    @Override
    public Boolean isPrepared(@Body Menu menu) {
        return null;
    }
}
