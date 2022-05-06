package rest;

import com.google.gson.Gson;
import dtos.PlayerDTO;
import dtos.UserDTO;
import entities.Player;
import entities.User;
import facades.PlayerFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("players")
public class PlayerResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;
    Gson GSON = new Gson();
    @Context
    SecurityContext securityContext;
    private PlayerFacade facade = PlayerFacade.getPlayerFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() {
        List<Player> players = facade.getAll();
        List<PlayerDTO> playerDTOS = PlayerDTO.getPlayerDTOs(players);
        return GSON.toJson(playerDTOS);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String getPlayerById(@PathParam("id") long id) {
        Player player = facade.getPlayer(id);
        PlayerDTO playerDTO = new PlayerDTO(player);
        return GSON.toJson(playerDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{name}/character")
    public String getCharacterName(@PathParam("name") String name) {
        int characterId = facade.getCharacterName(name);
        return GSON.toJson(characterId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/isalive")
    public String isAlive(@PathParam("id") long id) {
        boolean isAlive = facade.isAlice(id);
        return GSON.toJson(isAlive);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/name")
    public String getName(@PathParam("id") long id) {
        String name = facade.getName(id);
        return GSON.toJson(name);
    }


    //TODO: insert profile image in userclass
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("{id}/image")
//    public String getImageSource(@PathParam("id") long id) {
//        String imageSource = facade.getImageSource(id);
//        return GSON.toJson(imageSource);
//    }
}
