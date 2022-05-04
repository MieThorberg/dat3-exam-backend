package controller;

import java.util.List;
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

        Game game = gc.createGame(host);
       // gc.startGame(players);


    }

    @Test
    void createGame() {
        User user =new User("user", "test");
        Game game = gc.createGame(user);

        assertEquals(user, game.getHost());
        assertEquals(new ArrayList<>(), game.getPlayers());
        assertEquals(new ArrayList<>(), game.getVictims());
        assertEquals(0, game.getDays());
    }

    @Test
    void startGame() {
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

       // gc.startGame(players);


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