package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.MenuDto;
import org.apache.camel.Body;

public interface MenuService {
    Boolean isPrepared(MenuDto menu);
    Boolean menuExists(MenuDto menu);

}
