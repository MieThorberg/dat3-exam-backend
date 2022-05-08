package controller;

import entities.Game;
import entities.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class VoteController {
    private HashMap<Player, Integer> votes = new HashMap<>();

    // todo: handle blanc votes
    public Player startVotingCalculator(Game game){

        for (Player player : game.getPlayers()) {
            System.out.println(player.getUser().getUserName());
            Player playerVote = player.getLatestVote();
            if (playerVote != null){
                addVote(playerVote);
            }
        }

        // TODO: check for multiple players with highest votes
        if (votes.size() == 0) {
            return null;
        }
        return findResult();
    }

    protected void addVote(Player player){
        if(votes.containsKey(player)) {
            int vote = votes.get(player);
            vote++;
            votes.put(player, vote);
        } else {
            votes.put(player, 1);
        }
    }

    protected Player findResult(){
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
