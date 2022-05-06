package dtos;

public class CharacterDTO {

    private String name;
    private String description;
    private String imageSource;
    private int minPlayers;
    private int max;
    private String ability;

    public CharacterDTO() {
    }

    public CharacterDTO(String name, String description, String imageSource, int minPlayers, int max, String ability) {
        this.name = name;
        this.description = description;
        this.imageSource = imageSource;
        this.minPlayers = minPlayers;
        this.max = max;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }
}
