package controller;

import entities.Game;
import entities.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class VoteController {
    // private HashMap<Player, Player> voting = new HashMap<>();
    private HashMap<Player, Integer> votes = new HashMap<>();

    // todo: handle blanc votes

    public Player startVoting(Game game){

        for (Player player : game.getPlayers()) {
            Player playerVote = player.getVote();
            addVote(playerVote);
        }

        // TODO: check for multiple players with highest votes

        return findResult();
    }

    public void addVote(Player player){
        if(votes.containsKey(player)) {
            int vote = votes.get(player);
            vote++;
            votes.put(player, vote);
        } else {
            votes.put(player, 1);
        }
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
//        if(players.size() > 1){
//            return
//        }


        return players.get(0);
    }

    public HashMap<Player, Integer> getVotes() {
        return votes;
    }
}
