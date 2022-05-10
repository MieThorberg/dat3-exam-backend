package dtos;

import entities.*;


import java.util.ArrayList;
import java.util.List;

public class GameDTO {

    private long id;
    private String hostName;
    private long gamePin;
    private List<String> playerNames;
    private List<Long> nightRoundsID;
    private List<Long> dayRoundsID;
    private List<String> victimsNames;
    private String latestVictimName;
    private int days;


    public GameDTO(long id, String hostName, long gamePin, List<String> playerNames, List<Long> nightRoundsID, List<Long> dayRoundsID, List<String> victimsNames, String latestVictimName, int days) {
        this.id = id;
        this.hostName = hostName;
        this.gamePin = gamePin;
        this.playerNames = playerNames;
        this.nightRoundsID = nightRoundsID;
        this.dayRoundsID = dayRoundsID;
        this.victimsNames = victimsNames;
        this.latestVictimName = latestVictimName;
        this.days = days;
    }

    public GameDTO(Game game) {
        this.id = game.getId();
        this.gamePin = game.getGamePin();
        this.hostName = game.getHost().getUserName();
        this.playerNames = getPlayers(game.getPlayers());
        this.nightRoundsID = getNightRoundsIDS(game.getNightRounds());
        this.dayRoundsID = getDayRoundsIDS(game.getDayRounds());
        this.victimsNames = getPlayers(game.getVictims());
        this.days = game.getDays();

        if(game.getLatestVictim() != null) {
            this.latestVictimName = game.getLatestVictim().getUser().getUserName();
        }
    }

    public static List<GameDTO> getGameDTOs(List<Game> games) {
        List<GameDTO> gameDTOS = new ArrayList<>();
        for (Game game : games) {
            gameDTOS.add(new GameDTO(game));
        }
        return gameDTOS;
    }

    public List<String> getPlayers(List<Player> players){
        List<String> stringPlayer = new ArrayList<>();
        for (Player player : players)
        {
            stringPlayer.add(player.getUser().getUserName());
        }
        return stringPlayer;
    }

    public List<Long> getNightRoundsIDS(List<NightRound> rounds){
        List<Long> ids = new ArrayList<>();
        for (NightRound r : rounds)
        {
            ids.add(r.getId());
        }
        return ids;
    }
    public List<Long> getDayRoundsIDS(List<DayRound> rounds){
        List<Long> ids = new ArrayList<>();
        for (DayRound r : rounds)
        {
            ids.add(r.getId());
        }
        return ids;
    }

    public String getHostName() {
        return hostName;
    }

    public List<String> getPlayerNames() {
        return playerNames;
    }

    public List<Long> getNightRoundsID() {
        return nightRoundsID;
    }

    public List<Long> getDayRoundsID() {
        return dayRoundsID;
    }

    public List<String> getVictimsNames() {
        return victimsNames;
    }

    public String getLatestVictimName() {
        return latestVictimName;
    }

    public int getDays() {
        return days;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGamePin() {
        return gamePin;
    }

    public void setGamePin(long gamePin) {
        this.gamePin = gamePin;
    }
}
