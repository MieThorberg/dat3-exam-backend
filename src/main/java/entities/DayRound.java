package entities;

import javax.persistence.*;
import java.io.Serializable;

import controller.Timer;
import controller.VoteController;

@Entity
@Table (name = "DayRound")
public class DayRound extends Round implements Serializable {



    @Transient
    private VoteController voteController = new VoteController();

    public DayRound() {
    }

    public DayRound(Game game) {
        super(game.getDays(), game, true, null);
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
        Player victim =  voteController.startVotingCalculator(getGame());
        setVictim(victim);
    }
}
