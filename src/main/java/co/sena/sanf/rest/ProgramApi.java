package co.sena.sanf.rest;

import co.sena.sanf.domain.program.ProgramRegister;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.service.ProgramService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.UnknownHostException;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProgramApi {

    @Inject ProgramService programService;

    @GET
    @Path("/programs")
    public Response getListPrograms() throws DRException {
        return Response.ok()
            .entity(programService.getPrograms())
            .build();
    }

    @POST
    @Path("/programs")
    public Response addListPrograms(@Valid ProgramRegister programRegister) throws UnknownHostException {
        programService.addPrograms(programRegister);
        return Response.ok()
            .status(Response.Status.CREATED)
            .build();
    }
}
