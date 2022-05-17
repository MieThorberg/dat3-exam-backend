package dtos;

public class GameSettingDTO {

    private int amountOfWerewolves = 1;
    private boolean hasHunter = false;

    public GameSettingDTO() {
    }

    public int getAmountOfWerewolves() {
        return amountOfWerewolves;
    }

    public boolean hasHunter() {
        return hasHunter;
    }
}
