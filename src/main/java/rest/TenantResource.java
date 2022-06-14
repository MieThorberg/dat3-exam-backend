package rest;

import com.google.gson.Gson;
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
    @RolesAllowed({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response
                .ok()
                .entity(GSON.toJson(FACADE.getAll()))
                .build();
    }
}