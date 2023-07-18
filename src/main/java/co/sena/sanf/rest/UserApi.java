package co.sena.sanf.rest;

import co.sena.sanf.domain.User;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.UnknownHostException;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserApi {

    @Inject UserService userService;

    @GET
    @Path("/users")
    public Response getOneUser(String idUser) {
        return Response.ok().build();
    }

    @GET
    @Path("/users")
    public Response getListUsers() throws DRException {
        return Response.ok()
            .entity(userService.getAllUsers())
            .build();
    }

    @POST
    @Path("/users")
    public Response createUser(@Valid User user) throws DRException, UnknownHostException {
        userService.addOneUser(user);
        return Response.ok().status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/users")
    public Response updateUser(@Valid User user) throws DRException {
        userService.updateOneUser(user);
        return Response.ok()
            .status(Response.Status.NO_CONTENT)
            .build();
    }

    @DELETE
    @Path("/users/{idUser}")
    public Response deleteUser(@PathParam("idUser") String idUser) throws DRException {
        userService.deleteOneUser(idUser);
        return Response.ok()
            .status(Response.Status.NO_CONTENT)
            .build();
    }
}
