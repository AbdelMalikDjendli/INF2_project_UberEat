package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.resources.MenuRessource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class MenuRessourceTest {

    @InjectMocks
    private MenuRessource menuRessource;

    @Mock
    private MenuDAO menuDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMenus() {

        List<Menu> mockMenuList = Arrays.asList(
                createMenu("Ravioli", "Ravioli Fromage"),
                createMenu("Lasagna", "Garfields favorite dish"),
                createMenu("Penne", "Italian pasta")
        );

        when(menuDAO.getAllMenu()).thenReturn(mockMenuList);


        Response response = menuRessource.getMenus();


        assertEquals(200, response.getStatus());
        assertEquals(mockMenuList, response.getEntity());
    }

    private Menu createMenu(String name, String description) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setDescription(description);
        return menu;
    }
}

