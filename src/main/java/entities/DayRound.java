package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Queue;

@Entity
@Table (name = "Round")
public class DayRound extends Round implements Serializable {

    public DayRound() {
    }

    public DayRound(String victim,  Queue<Player> playerQueue) {
        super(victim, true, playerQueue);
    }

    @Override
    public void start() {

    }

    @Override
    public void vote() {

    }
}
