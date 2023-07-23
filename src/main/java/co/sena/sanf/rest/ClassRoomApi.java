package co.sena.sanf.rest;

import co.sena.sanf.domain.ClassRoom;
import co.sena.sanf.service.ClassRoomService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.UnknownHostException;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClassRoomApi {

    @Inject ClassRoomService classRoomService;

    @GET
    @Path("/class-rooms")
    public Response getClassRooms() {
        return Response.ok()
            .entity(classRoomService.getListClassRooms())
            .build();
    }

    @POST
    @Path("/class-rooms")
    public Response createListClassRooms(@Valid ClassRoom classRoom) throws UnknownHostException {
        classRoomService.addClassRooms(classRoom);
        return Response.ok()
            .status(Response.Status.CREATED)
            .build();
    }
}
