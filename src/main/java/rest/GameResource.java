package rest;

import com.google.gson.Gson;
import dtos.GameDTO;
import dtos.UserDTO;
import entities.Game;
import entities.Player;
import entities.User;
import facades.GameFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("game")
public class GameResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    Gson GSON = new Gson();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @POST
    @Path("creategame")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createGame(String data) {
        UserDTO userDTO = GSON.fromJson(data, UserDTO.class);
        User user = userDTO.toUser();
        Game game = GameFacade.getGameFacade(EMF).createGame(new Player(user));
        GameDTO gameDTO = new GameDTO(game);
        return GSON.toJson(gameDTO);
    }

    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGameById(@PathParam("id") long id) {
        Game game = GameFacade.getGameFacade(EMF).getGameById(id);
        GameDTO gameDTO = new GameDTO(game);
        return GSON.toJson(gameDTO);
    }

}
