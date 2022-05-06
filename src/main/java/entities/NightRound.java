package entities;

import controller.Timer;
import controller.VoteController;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "NightRound")
public class NightRound extends Round implements Serializable {

    @JoinColumn(name = "gameid")
    @ManyToOne
    private Game game;

    // TODO: make timer work
    @Transient
    private int nightTimer;
    @Transient
    VoteController voteController = new VoteController();

    public NightRound() {
    }
    public NightRound(Game game, PlayerQueue playerQueue, int nightTimer) {
        super(game.getDays(), false, playerQueue);
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
        Player victim =  voteController.startVotingCalculator(game);
        setVictim(victim);
    }

    public Game getGame() {
        return game;
    }
}
