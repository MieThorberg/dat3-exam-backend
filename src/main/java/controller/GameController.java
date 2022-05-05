package controller;

import entities.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class GameController {

    private PlayerQueue playerQueue;
    private int debateTime;
    private int votingTime;
    private int nightTime;
    private Game game;
    private Player latestVictim;

    public GameController() {
    }

    public GameController(Game game) {
        this.game = game;
        this.latestVictim = game.getLatestVictim();
    }

    public Game createGame(User user){
        this.game = new Game(user);
        return this.game;
    }

    public void startGame(ArrayList<Player> players, int amountOfWolves){
        game.setPlayers(players);
        characterAssigning(amountOfWolves);


        while (!hasEnded()){
            createRound();
        }
    }

    private void createRound(){
        //TODO: chance Night and Day Constructors

        // nightRound
        NightRound nightRound = new NightRound(game,playerQueue, nightTime);
        nightRound.start();
        kill(nightRound.getVictim());

        game.getNightRounds().add(nightRound);

        game.addDay();

        // who is dead
        // TODO: show latestVictim
        game.setLatestVictim(latestVictim = nightRound.getVictim());

        // dayRound
        DayRound dayRound = new DayRound(game, playerQueue, debateTime, votingTime);
        dayRound.start();
        kill(dayRound.getVictim());

        game.getDayRounds().add(dayRound);
    }


    private void kill(Player player){
        game.killPlayer(player);
    }

    private void characterAssigning(int amountOfWolves){
        // amount of player
        int amountOfPlayer = game.getPlayers().size();

        // check if the amount of werewolves makes sense, if not calculate a max amount of werewolves
        if (amountOfWolves > amountOfPlayer) {
            // todo: make better calculator for amount of werewolves
            amountOfWolves = ((amountOfPlayer/2) -1);
        }

        // amount of characters
        // how many characters are in game the game

        // assigning roles
        // make a Characters assign list
        List<Player> assignedPlayers = new ArrayList<>();

        // assign werewolves
        Random random = new Random();
        int wereWolfCharacterId = 1;
        for (int i = 0; i < amountOfWolves; i++) {
            int randomPicker = random.nextInt(game.getPlayers().size());

            // remove players from the player list, an assign them there role, and then put them in the assign list;
            Player pickPlayer = game.getPlayers().get(randomPicker);
            game.getPlayers().remove(randomPicker);

            pickPlayer.setCharacterId(wereWolfCharacterId);
            assignedPlayers.add(pickPlayer);
        }

        // assign other characters
        // remove players from the player list, an assign them there role, and then put them in the assign list;

        // assign villagers
        int villagerCharacterId = 0;

        // assign the rest of the player list as villagers
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setCharacterId(villagerCharacterId);
        }


        // merge the assign list into the player list
        game.getPlayers().addAll(assignedPlayers);

        for (Player player : game.getPlayers()) {
            System.out.println(player.getUser().getUserName() + " : " + player.getCharacterId());
        }
    }

    private boolean hasEnded(){
        return game.getWerewolves() > game.getPlayers().size() || game.getWerewolves() == 0;
    }

    public Player getLatestVictim() {
        return latestVictim;
    }

    public void setLatestVictim(Player latestVictim) {
        this.latestVictim = latestVictim;
    }
    public Player getVictim(){

        return null;
    }
}
