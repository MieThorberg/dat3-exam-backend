package dtos;

import entities.Game;
import entities.Player;
import entities.User;

public class PlayerDTO {

    private String username;
    private Long characterId;
    private Boolean isAlive;
    private Long gameId;

    public PlayerDTO(User user, long characterId, Boolean isAlive, Game game) {
        this.username = user.getUserName();
        this.characterId = characterId;
        this.isAlive = isAlive;
        this.gameId = game.getId();
    }

    public PlayerDTO(Player player) {
        this.username = player.getUser().getUserName();
        this.characterId = player.getCharacterId();
        this.isAlive = player.getAlive();
        this.gameId = player.getGame().getId();
    }
}
