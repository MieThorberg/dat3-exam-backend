package rest;

import com.google.gson.Gson;
import controller.GameController;
import dtos.*;
import entities.*;
import facades.GameFacade;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Path("games")
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

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGameById(@PathParam("id") long id) {
        Game game = GameFacade.getGameFacade(EMF).getGameById(id);
        GameDTO gameDTO = new GameDTO(game);
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

    @POST
    @Path("{id}/createplayers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPlayers(@PathParam("id") long id, String data) {
        UserDTO[] userDTO = GSON.fromJson(data, UserDTO[].class);
        List<Player> players = new ArrayList<>();

        for (UserDTO dto : userDTO) {
            User user = dto.toUser();
            players.add(new Player(user));
        }

        List<PlayerDTO> playerList = PlayerDTO.getPlayerDTOs(GameFacade.getGameFacade(EMF).createPlayers(id, players));

        return GSON.toJson(playerList);
    }

    @POST
    @Path("{id}/createplayer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPlayer(@PathParam("id") long id, String data){
        UserDTO userDTO = GSON.fromJson(data, UserDTO.class);
        User user = userDTO.toUser();
        Player player = new Player(user);
        Player newPlayer = GameFacade.getGameFacade(EMF).createPlayer(id,player);
        PlayerDTO playerDTO = new PlayerDTO(newPlayer);

        return GSON.toJson(playerDTO);
    }

    @GET
    @Path("{id}/players")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayersByGameId(@PathParam("id") long id) {
        List<Player> players = GameFacade.getGameFacade(EMF).getAllPlayersByGameId(id);
        List<PlayerDTO> playerDTOS = PlayerDTO.getPlayerDTOs(players);

        return GSON.toJson(playerDTOS);
    }

    // todo: virker ikke... fejl 500;
//    @GET
//    @Path("{id}/livingplayers")
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getLivingPlayersByGameId(@PathParam("id") long id) {
//        List<Player> players = GameFacade.getGameFacade(EMF).getAllLivingPlayers(id);
//        List<PlayerDTO> playerDTOS = PlayerDTO.getPlayerDTOs(players);
//
//        return GSON.toJson(playerDTOS);
//    }

    @GET
    @Path("{id}/victims")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllVictims(@PathParam("id") long id) {
        List<Player> victims = GameFacade.getGameFacade(EMF).getAllVictims(id);
        List<PlayerDTO> victimsDTOs = PlayerDTO.getPlayerDTOs(victims);
        return GSON.toJson(victimsDTOs);
    }

    @GET
    @Path("{id}/victims/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLatestVictims(@PathParam("id") long id) {
        Player victim = GameFacade.getGameFacade(EMF).getLatestVictim(id);
        PlayerDTO victimDTO = new PlayerDTO(victim);
        return GSON.toJson(victimDTO);
    }

    @GET
    @Path("{id}/days")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDays(@PathParam("id") long id) {
        int days = GameFacade.getGameFacade(EMF).getDays(id);

        return GSON.toJson(days);
    }

    @GET
    @Path("{id}/rounds")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRounds(@PathParam("id") long id) {
        List<NightRound> nightRounds = GameFacade.getGameFacade(EMF).getNightRounds(id);
        List<DayRound> dayRounds = GameFacade.getGameFacade(EMF).getDayRounds(id);

        List<GameRoundDTO> gameRoundDTOS = GameRoundDTO.getGameNightRoundDTO(nightRounds);
        gameRoundDTOS.addAll(GameRoundDTO.getGameDayRoundDTO(dayRounds));

        return GSON.toJson(gameRoundDTOS);
    }

    @GET
    @Path("{id}/rounds/{roundId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRounds(@PathParam("id") long id, @PathParam("roundId") long roundId) {
        NightRound nightRound = GameFacade.getGameFacade(EMF).getNightRoundsByID(id, roundId);
        DayRound dayRound = GameFacade.getGameFacade(EMF).getDayRoundsByID(id, roundId);


        GameRoundDTO gameRoundDTO;
        if (nightRound != null) {
            gameRoundDTO = new GameRoundDTO(nightRound);
        } else {
            gameRoundDTO = new GameRoundDTO(dayRound);
        }

        return GSON.toJson(gameRoundDTO);
    }


    @PUT
    @Path("{id}/{playerId}/vote")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String setPlayerVote(@PathParam("id") long id, @PathParam("playerId") long playerId, String data) {
        PlayerDTO playerDTO = GSON.fromJson(data, PlayerDTO.class);

        Player player = GameFacade.getGameFacade(EMF).setPlayerVote(playerId, playerDTO);
        playerDTO = new PlayerDTO(player);

        return GSON.toJson(playerDTO);
    }

    @GET
    @Path("{id}/voteresult")
    @Produces(MediaType.APPLICATION_JSON)
    public String getVoteResult(@PathParam("id") long id) {
        Player votedPlayer = GameFacade.getGameFacade(EMF).getVoteResult(id);
        PlayerDTO playerDTO = new PlayerDTO(votedPlayer);

        return GSON.toJson(playerDTO);
    }

    @PUT
    @Path("{id}/killplayer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String killPlayer(@PathParam("id") long id, String data) {
        PlayerDTO playerDTO = GSON.fromJson(data, PlayerDTO.class);
        Player player = GameFacade.getGameFacade(EMF).killPlayer(id, playerDTO);
        playerDTO = new PlayerDTO(player);

        return GSON.toJson(playerDTO);
    }

    @PUT
    @Path("{id}/assigncharacters")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String killPlayer(@PathParam("id") long id) {

        List<Player> players = GameFacade.getGameFacade(EMF).assignCharacters(id, 1);
        List<PlayerDTO> playerDTOS = PlayerDTO.getPlayerDTOs(players);

        return GSON.toJson(playerDTOS);
    }

    @GET
    @Path("{id}/hasended")
    @Produces(MediaType.APPLICATION_JSON)
    public String hasEnded(@PathParam("id") long id) {
        boolean hasEnded = GameFacade.getGameFacade(EMF).hasEnded(id);
        return GSON.toJson(hasEnded);
    }
}


