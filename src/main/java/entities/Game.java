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

    @JoinColumn(name = "Rounds")
    @OneToMany (mappedBy = "game")
    private List<NightRound> nightRounds;

    @JoinColumn(name = "Rounds")
    @OneToMany (mappedBy = "game")
    private List<DayRound> dayRounds;

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
    private int days = 0;

    public Game() {
    }

    public Game(User host, List<Player> players) {
        this.host = host;
        this.players = players;
    }

    public Game(User host) {
        this.host = host;
    }

    public void start(){

    }

    public void killPlayer(Player player){
        players.remove(player);
        victims.add(player);
    }

    public void addDay(){
        days++;
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

    public List<NightRound> getNightRounds() {
        return nightRounds;
    }

    public void setNightRounds(List<NightRound> nightRounds) {
        this.nightRounds = nightRounds;
    }

    public List<DayRound> getDayRounds() {
        return dayRounds;
    }

    public void setDayRounds(List<DayRound> dayRounds) {
        this.dayRounds = dayRounds;
    }

    public int getWerewolves() {
        int werewolfId = 1;
        int werewolves = 0;

        for (Player player : players) {
            if(player.getCharacterId() == werewolfId){
                werewolves++;
            }
        }

        return werewolves;
    }

}
