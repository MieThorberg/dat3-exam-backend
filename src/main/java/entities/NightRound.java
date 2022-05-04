package entities;

import controller.Timer;
import controller.VoteController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Queue;

@Entity
@Table (name = "Round")
public class NightRound extends Round implements Serializable {

    @JoinColumn(name = "gameid")
    @ManyToOne
    private Game game;

    // TODO: make timer work
    @Transient
    private int nightTimer;
    @Transient
    VoteController voteController;

    public NightRound() {
    }
    public NightRound(Game game, PlayerQueue playerQueue, int nightTimer) {
        super( false, playerQueue);
        this.game = game;
        this.nightTimer = nightTimer;
    }

    @Override
    public void start() {
        vote();
    }

    @Override
    public void vote() {
        Timer timer = new Timer();
        voteController.startVoting();

    }
}
