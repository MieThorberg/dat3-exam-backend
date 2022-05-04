package controller;

import entities.Game;
import entities.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class VoteController {
    // private HashMap<Player, Player> voting = new HashMap<>();
    private HashMap<Player, Integer> votes = new HashMap<>();

    public void startVoting(Game game){
        for (Player player : game.getPlayers()) {
            votes.put(player,0);
        }

        for (Player player : game.getPlayers()) {
            Player playerVote = player.getVote();

            int vote = votes.get(playerVote);
            vote++;
            votes.replace(playerVote, vote);
        }
    }

    public void addVote(Player player){

    }

    public Player findResult(){
        int maxValueInMap=(Collections.max(votes.values()));
        ArrayList<Player> players = new ArrayList<>();

        votes.forEach((player, vote) -> {
            if(maxValueInMap == vote){
                players.add(player);
            }
        });

        // TODO: check for multiple players with highest votes


        return players.get(0);
    }

    public HashMap<Player, Integer> getVotes() {
        return votes;
    }
}
