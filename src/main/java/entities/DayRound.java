package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Queue;

@Entity
@Table (name = "Round")
public class DayRound extends Round implements Serializable {

    @JoinColumn(name = "gameid")
    @ManyToOne
    private Game game;

    // TODO: make timer work
    @Transient
    private int debateTimer;
    @Transient
    private int votingTimer;

    public DayRound() {
    }

    public DayRound(Game game, Player victim,  Queue<Player> playerQueue) {
        super(victim, true, playerQueue);
        this.game = game;
    }

    @Override
    public void start() {

    }

    @Override
    public void vote() {

    }
}
