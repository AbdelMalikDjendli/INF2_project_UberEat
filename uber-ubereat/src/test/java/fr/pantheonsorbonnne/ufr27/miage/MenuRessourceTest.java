package fr.pantheonsorbonnne.ufr27.miage;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
        // Préparer les données pour le test
        List<Menu> mockMenus = new ArrayList<>();
        Menu mockMenu = new Menu();
        mockMenu.setId(1L);
        mockMenu.setName("Pizza Margherita");
        mockMenu.setDescription("Classic Italian pizza with tomatoes, mozzarella cheese, and basil");
        mockMenu.setPrice("8.99");
        mockMenus.add(mockMenu);

        when(menuDAO.getAllMenus()).thenReturn(mockMenus);

        // Appeler la méthode à tester
        Response response = menuRessource.getMenus();

        // Vérifier les résultats
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus(), "Le statut de la réponse devrait être 200 OK");
        assertEquals(mockMenus, response.getEntity(), "L'entité retournée doit être la liste des menus mock");

        // Vérifier les interactions avec le mock
        verify(menuDAO, times(1)).getAllMenus();
    }
}
