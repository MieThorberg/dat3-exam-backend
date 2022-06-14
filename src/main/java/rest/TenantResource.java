package rest;

import com.google.gson.Gson;
import dtos.HouseDTO;
import dtos.TenantDTO;
import facades.TenantFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tenants")
public class TenantResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final TenantFacade FACADE = TenantFacade.getTenantFacade(EMF);
    private static final Gson GSON = new Gson();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
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
        TenantDTO tenantDTO = GSON.fromJson(content, TenantDTO.class);
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.create(tenantDTO)))
                .build();
    }
}