package dtos;

import entities.DayRound;
import entities.NightRound;
import entities.Player;

import java.util.ArrayList;
import java.util.List;

public class GameRoundDTO {

    long id;
    boolean isDay;
    long gameId;
    String victimName;

    public GameRoundDTO(long id, boolean isDay, long gameId, String victimName) {
        this.id = id;
        this.isDay = isDay;
        this.gameId = gameId;
        this.victimName = victimName;
    }

    public GameRoundDTO(NightRound nightRound) {
        this.id = nightRound.getId();
        this.isDay = nightRound.isDay();
        this.gameId = nightRound.getGame().getId();
        if(nightRound.getVictim() != null) {
            this.victimName = nightRound.getVictim().getUser().getUserName();
        }
    }

    public GameRoundDTO(DayRound dayRound) {
        this.id = dayRound.getId();
        this.isDay = dayRound.isDay();
        this.gameId = dayRound.getGame().getId();
        if(dayRound.getVictim() != null) {
            this.victimName = dayRound.getVictim().getUser().getUserName();
        }
    }

    public static List<GameRoundDTO> getGameDayRoundDTO(List<DayRound> dayRounds) {
        List<GameRoundDTO> gameRoundDTOS = new ArrayList<>();
        for (DayRound dayRound : dayRounds) {
            gameRoundDTOS.add(new GameRoundDTO(dayRound));
        }
        return gameRoundDTOS;
    }
    public static List<GameRoundDTO> getGameNightRoundDTO(List<NightRound> nightRounds) {
        List<GameRoundDTO> gameRoundDTOS = new ArrayList<>();
        for (NightRound nightRound  : nightRounds) {
            gameRoundDTOS.add(new GameRoundDTO(nightRound));
        }
        return gameRoundDTOS;
    }
}
