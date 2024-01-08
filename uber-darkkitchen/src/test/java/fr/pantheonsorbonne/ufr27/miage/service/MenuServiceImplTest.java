package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.service.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


public class MenuServiceImplTest {

    @InjectMocks
    private MenuServiceImpl menuService;

    @Mock
    private MenuDAO menuDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsMenuPreparedByDk() {
        // Créez une liste de menus simulée
        List<Menu> mockMenuList = Arrays.asList(
                createMenu("Pizza", "Delicious pizza"),
                createMenu("Burger", "Tasty burger"),
                createMenu("Pasta", "Italian pasta")
        );

        // Définissez le comportement simulé du mock MenuDAO
        when(menuDAO.getAllMenu()).thenReturn(mockMenuList);

        // Testez la méthode isMenuPreparedByDk
        assertTrue(menuService.isMenuPreparedByDk("Pizza"));
        assertFalse(menuService.isMenuPreparedByDk("Salad"));
    }

    private Menu createMenu(String name, String description) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setDescription(description);
        return menu;
    }
}

