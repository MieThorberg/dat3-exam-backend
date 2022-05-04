package dtos;

import entities.Game;
import entities.Player;


import java.util.ArrayList;
import java.util.List;

public class GameDTO {

    private String hostName;
    private List<String> playerNames = new ArrayList<>();
    private boolean hasEnded;

    public GameDTO(Player host, List<Player> players, boolean hasEnded) {
        this.hostName = host.getUser().getUserName();
        this.playerNames = getPlayers(players);
        this.hasEnded = hasEnded;
    }

    public GameDTO(Game game) {
        this.hostName = game.getHost().getUserName();
        this.playerNames = getPlayers(game.getPlayers());
    }

    public List<String> getPlayers(List<Player> players){
        List<String> stringPlayer = new ArrayList<>();
        for (Player player : players)
        {
            stringPlayer.add(player.getUser().getUserName());
        }
        return stringPlayer;
    }


}
