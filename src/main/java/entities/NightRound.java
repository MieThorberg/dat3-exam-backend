package entities;

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
    private int NightTimer;

    public NightRound() {
    }
    public NightRound(Game game, Player victim, Queue<Player> playerQueue) {
        super(victim, false, playerQueue);
        this.game = game;
    }

    @Override
    public void start() {

    }

    @Override
    public void vote() {

    }
}
