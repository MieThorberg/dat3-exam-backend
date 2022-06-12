package rest;

import com.google.gson.Gson;
import dtos.DemoDTO;
import errorhandling.EntityNotFoundException;
import facades.DemoFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/demos")
public class DemoResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DemoFacade FACADE = DemoFacade.getDemoFacade(EMF);
    private static final Gson GSON = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getAll()))
                .build();
    }

    @GET
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) throws EntityNotFoundException {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getById(id)))
                .build();
    }

    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String content) {
        DemoDTO demoDTO = GSON.fromJson(content, DemoDTO.class);
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.create(demoDTO)))
                .build();
    }

    @PUT
    @Path("update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, String content) {
        DemoDTO demoDTO = GSON.fromJson(content, DemoDTO.class);
        demoDTO.setId(id);
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.update(demoDTO)))
                .build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id) throws EntityNotFoundException {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.delete(id)))
                .build();
    }
}