package entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Queue;

@Entity
@Table(name = "round")
public abstract class Round implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "victim")
    private String victim;

    @Column(name = "isDay")
    private boolean isDay;

    @Transient
    private Queue<Player> playerQueue;

    public Round() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVictim() {
        return victim;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    public Queue<Player> getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(Queue<Player> playerQueue) {
        this.playerQueue = playerQueue;
    }

    public abstract void start();
    public abstract void vote();



}
