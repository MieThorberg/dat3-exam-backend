package controller;

import entities.Game;
import entities.Player;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class VoteControllerTest {
    VoteController vc = new VoteController();

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

        playerHost.setLatestVote(player2);
        player.setLatestVote(player2);
        player1.setLatestVote(player2);
        player2.setLatestVote(player1);

        ArrayList<Player> players = new ArrayList<>();
        players.add(playerHost);
        players.add(player);
        players.add(player1);
        players.add(player2);

        Game game = new Game(host, players);
        vc.startVotingCalculator(game);

//        vc.getVotes().forEach((vPlayer, vote) -> {
//            System.out.println(vPlayer.getUser().getUserName() + " : " + vote);
//        });
    }

    @Test
    void startVoting() {
        int maxValueInMap=(Collections.max(vc.getVotes().values()));

        assertEquals(3, maxValueInMap);

        assertEquals(2, vc.getVotes().size());
    }

    @Test
    void addVote() {
        vc.addVote(new Player(new User("user5","test")));

        assertEquals(3, vc.getVotes().size());
    }

    @Test
    void findResult() {
        Player player = vc.findResult();

        assertEquals("user2", player.getUser().getUserName());
    }
}