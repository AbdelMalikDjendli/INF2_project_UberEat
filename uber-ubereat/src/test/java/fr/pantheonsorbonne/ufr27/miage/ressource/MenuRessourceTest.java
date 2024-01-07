package fr.pantheonsorbonne.ufr27.miage.ressource;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.resources.MenuRessource;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuRessourceTest {

    @Mock
    private MenuDAO menuDAO;

    @InjectMocks
    private MenuRessource menuRessource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMenus() {
        List<Menu> mockMenus = new ArrayList<>();
        Menu mockMenu = new Menu();
        mockMenu.setId(1L);
        mockMenu.setName("Pizza Margherita");
        mockMenu.setDescription("Tomato, Mozzarella, Basil");
        mockMenu.setPrice("12.00");
        mockMenus.add(mockMenu);

        when(menuDAO.getAllMenus()).thenReturn(mockMenus);

        Response response = menuRessource.getMenus();

        assertNotNull(response, "La réponse ne doit pas être null.");
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Le statut de la réponse doit être 200 OK.");
        assertEquals(mockMenus, response.getEntity(), "L'entité de la réponse doit être la liste des menus mock.");
    }
}
