package rest;

import com.google.gson.Gson;
import dtos.CharacterDTO;
import utils.EMF_Creator;
import utils.HttpUtils;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("characters")
public class CharactersResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() throws IOException {
        String data = HttpUtils.fetchData("https://miemt.me/werewolf_characters/api/characters");
        System.out.println(data);
        CharacterDTO[] characterDTOS = GSON.fromJson(data, CharacterDTO[].class);
        return GSON.toJson(characterDTOS);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCharacterById(@PathParam("id") long id) throws IOException {
        String data = HttpUtils.fetchData("https://miemt.me/werewolf_characters/api/characters/" + id);
        CharacterDTO characterDTO = GSON.fromJson(data, CharacterDTO.class);
        return GSON.toJson(characterDTO);
    }
}
