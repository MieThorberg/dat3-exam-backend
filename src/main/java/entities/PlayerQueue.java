package entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlayerQueue {

    private Queue<Player> playerQueue = new LinkedList<>();

    public PlayerQueue(List<Player> players) {
        setPlayers(players);
    }

    public void setPlayers(List<Player> players) {
        for (Player player : players) {
            playerQueue.add(player);
        }
    }

    public Player getHeadOfQueue() {
        return playerQueue.peek();
    }

    public Player getNextPlayer() {
        return playerQueue.remove();
    }

    public int getSize() {
        return playerQueue.size();
    }

    public void addPlayer(Player player) {
        playerQueue.add(player);
    }

    public boolean isEmpty() {
        return playerQueue.size() == 0;
    }
}
