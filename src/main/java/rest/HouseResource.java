package rest;

import com.google.gson.Gson;
import dtos.HouseDTO;
import facades.HouseFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/houses")
public class HouseResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final HouseFacade FACADE = HouseFacade.getHouseFacade(EMF);
    private static final Gson GSON = new Gson();

    @GET
    @RolesAllowed({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getAll()))
                .build();
    }

    @POST
    @Path("create")
    @RolesAllowed({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String content) {
        HouseDTO houseDTO = GSON.fromJson(content, HouseDTO.class);
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.create(houseDTO)))
                .build();
    }

}