package entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    User host = new User("Host", "test123");
    Game game = new Game(host, 1234);

    @BeforeEach
    void setUp() {
        Player pHost = new Player(host);
        Player player1 = new Player(new User("Player1", "test123"));
        Player player2 = new Player(new User("Player2", "test123"));
        Player player3 = new Player(new User("Player3", "test123"));

        player1.setCharacterName("werewolf");
        pHost.setCharacterName("villager");
        player2.setCharacterName("villager");
        player3.setCharacterName("villager");

        game.getPlayers().add(pHost);
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        game.getPlayers().add(player3);
    }

    @Test
    void killPlayer() {
        Player player = game.getPlayers().get(0);
        game.killPlayer(player);

        assertEquals(player, game.getVictims().get(0));
        assertEquals(1, game.getVictims().size());
    }

    @Test
    void addDay() {
        assertEquals(0, game.getDays());
        game.addDay();
        assertEquals(1, game.getDays());
    }

    @Test
    void getAmountOfWerewolves() {
        assertEquals(game.getAmountOfWerewolves(), 1);
    }

    @Test
    void getWerewolves() {
        assertEquals(1, game.getWerewolves().size());
        assertEquals("Player1", game.getWerewolves().get(0).getUser().getUserName());
    }

    @Test
    void hasEnded() {
        assertFalse(game.hasEnded());

        game.killPlayer(game.getWerewolves().get(0));

        assertTrue(game.hasEnded());
    }
}