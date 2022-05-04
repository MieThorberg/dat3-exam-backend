package controller;

import entities.*;

import java.util.ArrayList;

public class GameController {

    private PlayerQueue playerQueue;
    private int debateTime;
    private int votingTime;
    private int nightTime;
    private Game game;
    private Player latestVictim;

    public Game createGame(User user){
        this.game = new Game(user);
        return game;
    }

    public Game startGame(ArrayList<Player> players){
        game.setPlayers(players);
        characterAssigning();


        while (hasEnded()){
            createRound();
        }

        return game;
    }

    public void createRound(){
        //TODO: chance Night and Day Constructors

        // nightRound
        NightRound nightRound = new NightRound(game,playerQueue, nightTime);
        nightRound.start();
        kill(nightRound.getVictim());

        game.getNightRounds().add(nightRound);

        game.addDay();

        // who is dead
        // TODO: show latestVictim
        latestVictim = nightRound.getVictim();

        // dayRound
        DayRound dayRound = new DayRound(game, playerQueue, debateTime, votingTime);
        dayRound.start();
        kill(dayRound.getVictim());

        game.getDayRounds().add(dayRound);
    }

    public Player getVictim(){

        return null;
    }

    public void kill(Player player){
        game.killPlayer(player);
    }

    public void characterAssigning(){

    }

    public Player getLatestVictim() {
        return latestVictim;
    }

    public void setLatestVictim(Player latestVictim) {
        this.latestVictim = latestVictim;
    }

    public boolean hasEnded(){
        return game.getWerewolves() > game.getPlayers().size() || game.getWerewolves() == 0;
    }
}
