package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Menu;
import org.apache.camel.Body;

public interface MenuService {
    Boolean isPrepared(@Body Menu menu);
}
