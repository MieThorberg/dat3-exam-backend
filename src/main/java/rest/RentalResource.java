package rest;

import com.google.gson.Gson;
import dtos.HouseDTO;
import dtos.RentalDTO;
import facades.RentalFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rentals")
public class RentalResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final RentalFacade FACADE = RentalFacade.getRentalFacade(EMF);
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

    @GET
    @RolesAllowed({"admin"})
    @Path("id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long id) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getById(id)))
                .build();
    }

    @POST
    @Path("create")
    @RolesAllowed({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String content) {
        RentalDTO rentalDTO = GSON.fromJson(content, RentalDTO.class);
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.create(rentalDTO)))
                .build();
    }

    @GET
    @RolesAllowed({"admin"})
    @Path("tenantsfromhouse/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTenantsFromHouseById(@PathParam("id") long id) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getTenantsFromHouseById(id)))
                .build();
    }

    @GET
    @RolesAllowed({"user"})
    @Path("rentalsfromtenant/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRentalByTenantName(@PathParam("name") String name) {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getRentalByTenantName(name)))
                .build();
    }
}