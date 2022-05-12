package dtos;

import entities.Game;
import entities.Player;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {

    private long id;
    private String username;
    private String characterName;
    private boolean isAlive;
    private long gameId;
    private String latestVote;
    private boolean isHost;

    public PlayerDTO(long id, String username, String characterName, boolean isAlive, long gameId, String latestVote, boolean isHost) {
        this.id = id;
        this.username = username;
        this.characterName = characterName;
        this.isAlive = isAlive;
        this.gameId = gameId;
        this.latestVote = latestVote;
        this.isHost = isHost;
    }

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.username = player.getUser().getUserName();
        this.characterName = player.getCharacterName();
        this.isAlive = player.getAlive();
        this.gameId = player.getGame().getId();
        this.isHost = player.isHost();

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

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterId) {
        this.characterName = characterId;
    }

    public boolean getAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
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
