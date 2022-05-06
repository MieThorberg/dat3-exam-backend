package entities;

import javax.persistence.*;
import java.io.Serializable;

import controller.Timer;
import controller.VoteController;

@Entity
@Table (name = "DayRound")
public class DayRound extends Round implements Serializable {

    @JoinColumn(name = "gameid")
    @ManyToOne
    private Game game;

    @Transient
    private VoteController voteController = new VoteController();

    public DayRound() {
    }

    public DayRound(Game game) {
        super(game.getDays(), true, null);
        this.game = game;
        game.getDayRounds().add(this);
    }

    @Override
    public void start() {
        debate();
        vote();
    }

    private void debate(){
        Timer timer = new Timer();
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
