package controller;

import entities.Game;
import entities.Player;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    GameController gc = new GameController();

    @BeforeEach
    void setUp() {
        User host = new User("host", "test");
        User user = new User("user", "test");
        User user1 = new User("user1", "test");
        User user2 = new User("user2", "test");

        Player playerHost = new Player(host);
        Player player = new Player(user);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(playerHost);
        players.add(player);
        players.add(player1);
        players.add(player2);

        Game game = gc.createGame(host, 1234);
       // gc.startGame(players);


    }

    @Test
    void createGame() {
        User user =new User("user", "test");
        Game game = gc.createGame(user,1234);

        assertEquals(user, game.getHost());
        assertEquals(new ArrayList<>(), game.getPlayers());
        assertEquals(new ArrayList<>(), game.getVictims());
        assertEquals(0, game.getDays());
    }

    @Test
    void startGame() {
        // todo: test not working properly

        User host = new User("host", "test");
        User user = new User("user", "test");
        User user1 = new User("user1", "test");
        User user2 = new User("user2", "test");

        Player playerHost = new Player(host);
        Player player = new Player(user);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);

        ArrayList<Player> players = new ArrayList<>();
        players.add(playerHost);
        players.add(player);
        players.add(player1);
        players.add(player2);

        playerHost.setLatestVote(player1);
        player.setLatestVote(player1);
        player2.setLatestVote(player1);
        player1.setLatestVote(player2);

        player1.setCharacterName("werewolf");

       // gc.startGame(players, 1);


    }

    @Test
    void createRound() {

    }

    @Test
    void getVictim() {
    }

    @Test
    void kill() {
    }

    @Test
    void characterAssigning() {
    }

    @Test
    void getLatestVictim() {
    }

    @Test
    void setLatestVictim() {
    }

    @Test
    void hasEnded() {
    }
}