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

    @Transient
    VoteController voteController = new VoteController();

    public NightRound() {
    }
    public NightRound(Game game) {
        super(game.getDays(), false, null);
        this.game = game;
        game.getNightRounds().add(this);
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
