package controller;

import entities.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class CharacterController {

    private Game game;

    public CharacterController(Game game) {
        this.game = game;
    }

    public void characterAssigning(int amountOfWolves, boolean hasHunter) {
        // amount of player
        int amountOfPlayer = game.getPlayers().size();

        // check if the amount of werewolves makes sense, if not calculate a max amount of werewolves
        if (amountOfWolves >= (amountOfPlayer / 2)) {
            amountOfWolves = (int) (Math.ceil((double) amountOfPlayer / 2) - 1);
        }
        if(amountOfWolves <= 0) {
            amountOfWolves = 1;
        }

        //TODO:
        // amount of characters
        // how many characters are in game the game

        // assigning roles
        // make a Characters assign list
        List<Player> assignedPlayers = new ArrayList<>();

        // assign werewolves
        Random random = new Random();
        String werewolfCharacter = "werewolf";
        for (int i = 0; i < amountOfWolves; i++) {
            int randomPicker = random.nextInt(game.getPlayers().size());

            // remove players from the player list, an assign them there role, and then put them in the assign list;
            Player pickPlayer = game.getPlayers().get(randomPicker);
            game.getPlayers().remove(randomPicker);

            pickPlayer.setCharacterName(werewolfCharacter);
            assignedPlayers.add(pickPlayer);
        }

        //TODO:
        // assign other characters
        // remove players from
        // the player list, an assign them there role, and then put them in the assign list;

        if(hasHunter && amountOfPlayer >= 3){
            String hunterCharacter = "hunter";
            int randomPicker = random.nextInt(game.getPlayers().size());

            Player pickPlayer = game.getPlayers().get(randomPicker);
            game.getPlayers().remove(randomPicker);

            pickPlayer.setCharacterName(hunterCharacter);
            assignedPlayers.add(pickPlayer);
        }


        // assign villagers
        // assign the rest of the player list as villagers
        String villagerName = "villager";
        for (int i = 0; i < game.getPlayers().size(); i++) {
            game.getPlayers().get(i).setCharacterName(villagerName);
        }


        // merge the assign list into the player list
        game.getPlayers().addAll(assignedPlayers);

        for (Player player : game.getPlayers()) {
            System.out.println(player.getUser().getUserName() + " : " + player.getCharacterName());
        }

        /* end og method **/

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

}
