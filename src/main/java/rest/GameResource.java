package rest;

import com.google.gson.Gson;
import controller.GameController;
import dtos.*;
import entities.*;
import facades.GameFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("games")
public class GameResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    Gson GSON = new Gson();
    GameController GC;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllGames() {
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
    @Path("{id}/createnightround")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createNightRound(@PathParam("id") long id) {
        NightRound nightRound = GameFacade.getGameFacade(EMF).createNightRound(id);
        GameRoundDTO gameRoundDTO = new GameRoundDTO(nightRound);

        return GSON.toJson(gameRoundDTO);
    }

    @POST
    @Path("{id}/createdayround")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDayRound(@PathParam("id") long id) {
        DayRound dayRound = GameFacade.getGameFacade(EMF).createDayRound(id);
        GameRoundDTO gameRoundDTO = new GameRoundDTO(dayRound);

        return GSON.toJson(gameRoundDTO);
    }

    @POST
    @Path("{id}/createplayer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPlayer(@PathParam("id") long id, String data) {
        UserDTO userDTO = GSON.fromJson(data, UserDTO.class);
        User user = userDTO.toUser();
        Player player = new Player(user);
        Player newPlayer = GameFacade.getGameFacade(EMF).createPlayer(id, player);
        PlayerDTO playerDTO = new PlayerDTO(newPlayer);

        return GSON.toJson(playerDTO);
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

    @GET
    @Path("{id}/players")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayersByGameId(@PathParam("id") long id) {
        List<Player> players = GameFacade.getGameFacade(EMF).getAllPlayersByGameId(id);
        List<PlayerDTO> playerDTOS = PlayerDTO.getPlayerDTOs(players);

        return GSON.toJson(playerDTOS);
    }

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
    @Path("{id}/aliveplayers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAlivePlayersByGameId(@PathParam("id") long id) {
        List<Player> players = GameFacade.getGameFacade(EMF).getAllAlivePlayers(id);
        List<PlayerDTO> playerDTOS = PlayerDTO.getPlayerDTOs(players);

        return GSON.toJson(playerDTOS);
    }

    @GET
    @Path("{id}/getwerewolves")
    @Produces(MediaType.APPLICATION_JSON)
    public String getWerewolves(@PathParam("id") long id) {
        List<Player> werewolves = GameFacade.getGameFacade(EMF).getWerewolves(id);
        List<PlayerDTO> playerDTOs = PlayerDTO.getPlayerDTOs(werewolves);

        return GSON.toJson(playerDTOs);
    }

    @GET
    @Path("{id}/day")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDays(@PathParam("id") long id) {
        int days = GameFacade.getGameFacade(EMF).getDay(id);

        return GSON.toJson(days);
    }

    @PUT
    @Path("{id}/addday")
    @Produces(MediaType.APPLICATION_JSON)
    public String addDay(@PathParam("id") long id) {
        int days = GameFacade.getGameFacade(EMF).addDays(id);
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

    @GET
    @Path("{id}/rounds/{day}/getnightround")
    @Produces(MediaType.APPLICATION_JSON)
    public String getNightRoundsByDay(@PathParam("id") long id, @PathParam("day") int day) {
        NightRound nightRound = GameFacade.getGameFacade(EMF).getNightRoundsByDay(id, day);

        GameRoundDTO gameRoundDTO = new GameRoundDTO(nightRound);


        return GSON.toJson(gameRoundDTO);
    }

    @GET
    @Path("{id}/rounds/{day}/getdayround")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDayRoundsByDay(@PathParam("id") long id, @PathParam("day") int day) {
        DayRound dayRound = GameFacade.getGameFacade(EMF).getDayRoundsByDay(id, day);
        GameRoundDTO gameRoundDTO = new GameRoundDTO(dayRound);

        return GSON.toJson(gameRoundDTO);
    }

    @PUT
    @Path("{id}/rounds/nightroundresult")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String nightRoundResult(@PathParam("id") long id) {
        NightRound nightRound = GameFacade.getGameFacade(EMF).nightRoundResult(id);

        return GSON.toJson(new GameRoundDTO(nightRound));
    }

    @PUT
    @Path("{id}/rounds/dayroundresult")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String dayRoundResult(@PathParam("id") long id) {
        DayRound dayRound = GameFacade.getGameFacade(EMF).dayRoundResult(id);

        return GSON.toJson(new GameRoundDTO(dayRound));
    }

    @GET
    @Path("{id}/rounds/current")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCurrentRound(@PathParam("id") long id) {
        NightRound nightRound = GameFacade.getGameFacade(EMF).getCurrentNightRound(id);
        DayRound dayRound = GameFacade.getGameFacade(EMF).getCurrentDayRound(id);

        GameRoundDTO gameRoundDTO;
        if (dayRound == null || nightRound == null) {
            if (dayRound == null && nightRound == null) {
                return null;
            } else if (dayRound == null) {
                gameRoundDTO = new GameRoundDTO(nightRound);
            } else {
                gameRoundDTO = new GameRoundDTO(dayRound);
            }
        } else {
            if (nightRound.getId() > dayRound.getId()) {
                gameRoundDTO = new GameRoundDTO(nightRound);
            } else {
                gameRoundDTO = new GameRoundDTO(dayRound);
            }
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

        if (votedPlayer == null) {
            return GSON.toJson("no result");
        }

        PlayerDTO playerDTO = new PlayerDTO(votedPlayer);
        return GSON.toJson(playerDTO);
    }

    @PUT
    @Path("{id}/cleanvotes")
    @Produces(MediaType.APPLICATION_JSON)
    public void cleanVotes(@PathParam("id") long id) {
        GameFacade.getGameFacade(EMF).cleanVotes(id);
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
    public String assignCharacters(@PathParam("id") long id) {
        // TODO: receive a list with character roles and amount
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


