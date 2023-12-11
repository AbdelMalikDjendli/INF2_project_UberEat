package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import fr.pantheonsorbonne.ufr27.miage.service.MenuService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/menus")
public class MenuRessource {
    private final MenuService menuService;

    public MenuRessource(MenuService menuService) {
        this.menuService = menuService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenus() {
        List<Menu> menus = menuService.getAllMenus();
        return Response.ok(menus).build();
    }
}
