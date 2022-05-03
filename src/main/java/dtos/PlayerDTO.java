package dtos;

import entities.Character;
import entities.Game;
import entities.Player;
import entities.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

public class PlayerDTO {

    private String username;
    private Long characterId;
    private Boolean isAlive = true;
    private Long gameId;

    public PlayerDTO(User user, Character character, Boolean isAlive, Game game) {
        this.username = user.getUserName();
        this.characterId = character.getId();
        this.isAlive = isAlive;
        this.gameId = game.getId();
    }

    public PlayerDTO(Player player) {
        this.username = player.getUser().getUserName();
        this.characterId = player.getCharacter().getId();
        this.isAlive = player.getAlive();
        this.gameId = player.getGame().getId();
    }
}
