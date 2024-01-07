package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dao.MenuDAO;
import fr.pantheonsorbonne.ufr27.miage.model.Menu;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/menus")
public class MenuRessource {

    @Inject
    MenuDAO menuDAO;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenus() {
        List<Menu> menus = menuDAO.getAllMenus();
        return Response.ok(menus).build();
    }
}
