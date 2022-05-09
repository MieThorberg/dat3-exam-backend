package entities;

import controller.GameController;
import controller.VoteController;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "NightRound")
public class NightRound extends Round implements Serializable {

    @Transient
    VoteController voteController = new VoteController();

    public NightRound() {
    }
    public NightRound(Game game) {
        super(game.getDays(), game, false, null);
        game.getNightRounds().add(this);
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
