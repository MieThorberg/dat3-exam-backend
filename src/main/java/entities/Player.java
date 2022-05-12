package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "players")
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "isHost")
    private boolean isHost = false;

    @OneToOne
    private User user;

    @Column(name = "characterName")
    private String characterName;

    @NotNull
    @Column(name = "isAlive")
    private Boolean isAlive = true;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne
    private Player latestVote;

    public Player() {
    }

    public Player(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        game.getPlayers().add(this);
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterId) {
        this.characterName = characterId;
    }

    public Player getLatestVote() {
        return latestVote;
    }

    public void setLatestVote(Player vote) {
        this.latestVote = vote;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
