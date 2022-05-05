package rest;

import com.google.gson.Gson;
import controller.GameController;
import dtos.CharacterDTO;
import dtos.GameDTO;
import dtos.UserDTO;
import entities.Game;
import entities.Player;
import entities.User;
import facades.GameFacade;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@Path("game")
public class GameResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    Gson GSON = new Gson();
    GameController GC;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        List<Game> games = GameFacade.getGameFacade(EMF).getAllGames();
        List<GameDTO> gameDTO = GameDTO.getGameDTOs(games);
        return GSON.toJson(gameDTO);
    }

    @POST
    @Path("creategame")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createGame(String data) {
        UserDTO userDTO = GSON.fromJson(data, UserDTO.class);
        User user = userDTO.toUser();
        Game game = GameFacade.getGameFacade(EMF).createGame(user);
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
