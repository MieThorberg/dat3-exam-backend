package controller;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // check if the amount of werewolves makes sense, if not calculate a max amount of werewolves


        // amount of characters
        // how many characters are in game the game


        // assigning roles
        // assign werewolves
        // make a Characters assign list
        // remove players from the player list, an assign them there role, and then put them in the assign list;

        // assign other characters
        // remove players from the player list, an assign them there role, and then put them in the assign list;

        // assign villagers
        // assign the rest of the player list as villagers

        // merge the assign list into the player list





//        List<Player> playersLeft = game.getPlayers();
//        HashMap<Integer, Integer> characters = game.getCharacters();

        //random assign players

        //gennemgå alle characterids
//        for (Integer key : characters.keySet()) {
//            int characterId = key;
//            int amount = characters.get(characterId);
//            int size = playersLeft.size();
//
            //for hver character har den en amount
            // vi assign tilfældige players i et loop indtil vi når amount værdien
//            for(int i = 0; i < amount; i++) {
//                int randomIndex = getRandomIndex(size);
//                playersLeft.get(randomIndex).setCharacterId(characterId);
//                playersLeft.remove(randomIndex);
//            }
//        }

        //merge liste til game player

    }

//    public int getRandomIndex(int size) {
//        Random random = new Random();
//        return random.nextInt(size);
//    }

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
