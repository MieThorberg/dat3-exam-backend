package entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Queue;
import controller.Timer;

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

    public DayRound(Game game,  PlayerQueue playerQueue, int debateTimer, int votingTimer) {
        super( true, playerQueue);
        this.game = game;
        this.debateTimer = debateTimer;
        this.votingTimer = votingTimer;
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

    }
}
