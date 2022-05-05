package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Queue;

@MappedSuperclass
@Table(name = "round")
public abstract class Round implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id", nullable = false)
    private long id;

    @JoinColumn(name = "victim")
    @OneToOne
    private Player victim;

    @Column(name = "isDay")
    private boolean isDay;

    @Transient
    private PlayerQueue playerQueue;

    public Round() {
    }

    public Round(boolean isDay, PlayerQueue playerQueue) {
        this.isDay = isDay;
        this.playerQueue = playerQueue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getVictim() {
        return victim;
    }

    public void setVictim(Player victim) {
        this.victim = victim;
    }

    public PlayerQueue getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(PlayerQueue playerQueue) {
        this.playerQueue = playerQueue;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }

    public abstract void start();
    public abstract void vote();



}
