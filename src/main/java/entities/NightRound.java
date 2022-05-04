package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Queue;

@Entity
@Table (name = "Round")
public class NightRound extends Round implements Serializable {

    public NightRound() {
    }

    public NightRound(String victim, boolean isDay, Queue<Player> playerQueue) {
        super(victim, isDay, playerQueue);
    }

    @Override
    public void start() {

    }

    @Override
    public void vote() {

    }
}
