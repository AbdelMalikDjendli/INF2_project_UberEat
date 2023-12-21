package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/menus")
public class MenuRessource {
    private final MenuDAO menuService;

    public MenuRessource(MenuDAO menuService) {
        this.menuService = menuService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenus() {
        List<Menu> menus = menuService.getAllMenu();
        return Response.ok(menus).build();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkMenu() {
        List<Menu> menus = menuService.getAllMenu();
        return Response.ok(menus).build();
    }
}
