package co.sena.sanf.rest;

import co.sena.sanf.domain.Competences;
import co.sena.sanf.service.CompetencesService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;

@Path("/internal")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompetencesApi {

    @Inject CompetencesService competencesService;

    @GET
    @Path("/competences")
    public Response getListCompetences() {
        return Response.ok()
            .build();
    }

    @GET
    @Path("/competences/{idCompetence}")
    public Response getCompetenceById(@PathParam("idCompetence") String idCompetences) {
        return Response.ok()
            .entity(competencesService.getCompetences(idCompetences))
            .build();
    }

    @POST
    @Path("/competences")
    public Response addListCompetences(Competences competences) throws UnknownHostException {
        competencesService.addCompetencesList(competences);
        return Response.ok().status(Response.Status.CREATED).build();
    }
}
