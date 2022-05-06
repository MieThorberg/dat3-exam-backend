package dtos;

import entities.Game;
import entities.Player;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {

    private long id;
    private String username;
    private Long characterId;
    private Boolean isAlive;
    private Long gameId;
    private String latestVote;

    public PlayerDTO(long id, User user, long characterId, Boolean isAlive, Game game, String latestVote) {
        this.id = id;
        this.username = user.getUserName();
        this.characterId = characterId;
        this.isAlive = isAlive;
        this.gameId = game.getId();
        this.latestVote = latestVote;
    }

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.username = player.getUser().getUserName();
        this.characterId = player.getCharacterId();
        this.isAlive = player.getAlive();
        this.gameId = player.getGame().getId();

        if (player.getLatestVote() != null){
            this.latestVote = player.getLatestVote().getUser().getUserName();
        }
    }

    public static List<PlayerDTO> getPlayerDTOs(List<Player> players) {
        List<PlayerDTO> playerDTOS = new ArrayList<>();
        for (Player player : players) {
            playerDTOS.add(new PlayerDTO(player));
        }
        return playerDTOS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Long characterId) {
        this.characterId = characterId;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getLatestVote() {
        return latestVote;
    }

    public void setLatestVote(String latestVote) {
        this.latestVote = latestVote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
