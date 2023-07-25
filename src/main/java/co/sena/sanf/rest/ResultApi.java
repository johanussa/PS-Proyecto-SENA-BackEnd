package co.sena.sanf.rest;

import co.sena.sanf.domain.result.ResultRegister;
import co.sena.sanf.service.ResultService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.UnknownHostException;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResultApi {

    @Inject ResultService resultService;

    @GET
    @Path("/results")
    public Response getListResults() {
        return Response.ok()
            .entity(resultService.getListResults())
            .build();
    }

    @GET
    @Path("/results/{idCompetence}")
    public Response getResultsByCompetence(@PathParam("idCompetence") String idCompetence) {
        return Response.ok()
            .build();
    }

    @POST
    @Path("/results")
    public Response addResults(ResultRegister result) throws UnknownHostException {
        resultService.addResults(result);
        return Response.ok()
            .status(Response.Status.CREATED)
            .build();
    }
}
