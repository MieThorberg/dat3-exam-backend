package dtos;

public class CharacterDTO {

    private Long id;
    private String name;
    private String description;
    private String imageSource;

    public CharacterDTO(Long id, String name, String description, String imageSource) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageSource = imageSource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
