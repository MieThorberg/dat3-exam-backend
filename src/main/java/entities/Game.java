package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @OneToOne
    @NotNull
    private User host;

    @NotNull
    @JoinColumn(name = "players")
    @OneToMany(mappedBy = "game")
    private List<Player> players = new ArrayList<>();

    @JoinTable(name = "game_player", joinColumns = {@JoinColumn(name = "gameid", referencedColumnName = "ID")},
            inverseJoinColumns={ @JoinColumn(name="playerid", referencedColumnName="ID", unique=true) }
    )
    @JoinColumn(name = "victims")
    @OneToMany(mappedBy = "game")
    private List<Player> victims = new ArrayList<>();

    @NotNull
    @Column(name = "days")
    private int days;

    public Game() {
    }

    public Game(User host, List<Player> players, List<Player> victims, int days) {
        this.host = host;
        this.players = players;
        this.victims = victims;
        this.days = days;
    }

    public Game(User host) {
        this.host = host;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
