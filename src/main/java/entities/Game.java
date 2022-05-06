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
    private List<NightRound> nightRounds = new ArrayList<>();

    @JoinColumn(name = "Rounds")
    @OneToMany (mappedBy = "game")
    private List<DayRound> dayRounds = new ArrayList<>();

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

    @OneToOne
    @NotNull
    @JoinColumn(name = "latestVictim")
    private Player latestVictim;

    @NotNull
    @Column(name = "days")
    private int days = 0;


    //hav et felt til at holde styr på character og antallet af characters
//    @Transient
//    //key: characterid , value: amount
//    private HashMap<Integer, Integer> characters;

    public Game() {
    }

    public Game(User host, List<Player> players) {
        this.host = host;
        this.players = players;
    }

    public Game(User host) {
        this.host = host;
    }

    public void killPlayer(Player player){
        players.remove(player);
        player.setAlive(false);
        setLatestVictim(player);
        victims.add(player);
    }

    public void addDay(){
        days++;
    }

    public int getWerewolves() {
        int werewolves = 0;

        for (Player player : players) {
            if(player.getCharacterName().equals("werewolf")){
                werewolves++;
            }
        }

        return werewolves;
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

    public List<Player> getVictims() {
        return victims;
    }

    public void setVictims(List<Player> victims) {
        this.victims = victims;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Player getLatestVictim() {
        return latestVictim;
    }

    public void setLatestVictim(Player latestVictim) {
        this.latestVictim = latestVictim;
    }

//    public HashMap<Integer, Integer> getCharacters() {
//        return characters;
//    }
//
//    public void setCharacters(HashMap<Integer, Integer> characters) {
//        this.characters = characters;
//    }
//
//    public void addCharacter(int characterId, int amount) {
//         characters.put(characterId, amount);
//    }


}
