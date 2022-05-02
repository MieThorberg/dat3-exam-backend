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
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "host")
    private Player host;

    @NotNull
    @JoinColumn(name = "players")
    @OneToMany(mappedBy = "game")
    private List<Player> players = new ArrayList<>();

    @NotNull
    @Column(name = "hasEnded")
    private boolean hasEnded;

    public Game() {
    }

    public Game(Player host, List<Player> players) {
        this.host = host;
        this.players = players;
    }
}
