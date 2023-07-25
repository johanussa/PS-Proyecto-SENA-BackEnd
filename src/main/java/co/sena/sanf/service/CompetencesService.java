package co.sena.sanf.service;

import co.sena.sanf.domain.Meta;
import co.sena.sanf.domain.competences.CompetencesRegister;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.repository.CompetencesRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UnwindOptions;
import io.github.cdimascio.dotenv.Dotenv;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

@ApplicationScoped
public class CompetencesService {

    @Inject Logger LOG;
    @Inject MongoClient mongoClient;
    @Inject CompetencesRepository competencesRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public Document getCompetences(String idCompetences) throws DRException {
        LOG.infof("@getCompetences API > Inicia servicio para obtener la lista de competencias de id: %s",
            idCompetences);
        return getPipeline(idCompetences);
    }

    public void addCompetencesList(CompetencesRegister competences) throws UnknownHostException {
        LOG.infof("@addCompetencesList API > Inicia servicio para registrar listado de competencias con la " +
            "data: %s", competences.getData());
        CompetencesRegister register = CompetencesRegister.builder()
            .meta(
                Meta.builder()
                    .creationDate(LocalDateTime.now())
                    .source(httpServerRequestProvider.get().absoluteURI())
                    .ipAddress(InetAddress.getLocalHost().getHostAddress())
                    .type("Competencias-Programas-CME")
                    .tokenRaw("Bearer bGciOiJSUzI1NiIsIhPqZCI6I")
                    .build()
            )
            .data(competences.getData())
            .build();
        competencesRepository.persist(register);
        LOG.infof("@addCompetencesList API > El registro de la lista de competencias fue exitoso");
        LOG.infof("@addCompetencesList API > Inicia servicio para registrar listado de competencias con la " +
            "data: %s", competences.getData());
    }

    private List<Bson> getListQuery(ObjectId id) {
        LOG.infof("@getListQuery API > Inicia servicio para obtener pipeline de consulta para el id: %s", id);
        Bson stage_0_match = match(eq("_id", id));
        Bson stage_1_lookup = lookup(
            "SENA_PROG-RESULTS", "data.resultados", "_id", "results"
        );
        Bson stage_2_unwind = unwind("$results", new UnwindOptions().preserveNullAndEmptyArrays(true));
        Bson stage_3_project = project(
            fields(
                computed("data.competencias", "$data.competencias"),
                computed("data.resultados", "$results.data"),
                excludeId()
            )
        );
        List<Bson> pipeline = new ArrayList<>();
        pipeline.add(stage_0_match);
        pipeline.add(stage_1_lookup);
        pipeline.add(stage_2_unwind);
        pipeline.add(stage_3_project);

        LOG.infof("@getListQuery API > Finaliza servicio para obtener pipeline de consulta para el id: %s", id);
        return pipeline;
    }

    private MongoCollection<Document> getCollection (){
        Dotenv dotenv = Dotenv.configure().load();
        MongoDatabase db = mongoClient.getDatabase(dotenv.get("DATABASE"));
        return db.getCollection(dotenv.get("COLLECTION_COMPETENCES"));
    }

    private Document getPipeline(String idCompetences) throws DRException {
        validObjectId(idCompetences);
        List<Bson> queries = getListQuery(new ObjectId(idCompetences));
        Optional<Document> doc = getCollection().aggregate(queries).allowDiskUse(true)
            .into(new ArrayList<>()).stream().findFirst();
        verifyDocumentState(doc.isPresent());
        LOG.infof("@getPipeline API > La lista de competencias y resultados se obtuvieron correctamente");
        LOG.infof("@getCompetences API > Finaliza servicio para obtener la lista de competencias de id: %s",
            idCompetences);
        return doc.orElseThrow();
    }

    private void validObjectId(String id) throws DRException {
        if (ObjectId.isValid(id)) return;
        LOG.errorf("@validObjectId API > El id: %s ingresado No es un identificador valido", id);
        throw new DRException(
            Response.Status.BAD_REQUEST.getStatusCode(),
            "El id: " + id + " ingresado No es un identificador valido"
        );
    }

    private void verifyDocumentState(boolean state) throws DRException {
        if (state) return;
        LOG.infof("@verifyDocumentState API > No se encontraron registros para la competencia indicada");
        throw new DRException(
            Response.Status.NOT_FOUND.getStatusCode(),
            "No se encontraron registros para la competencia indicada"
        );
    }
}
