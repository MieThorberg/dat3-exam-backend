package entities;

import javax.persistence.*;
import java.io.Serializable;

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
        voteResult();
        getGame().killPlayer(getVictim());
    }

    @Override
    public void voteResult() {
        Player victim =  voteController.startVotingCalculator(getGame());
        setVictim(victim);
    }
}
