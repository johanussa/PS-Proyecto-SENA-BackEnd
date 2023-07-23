package co.sena.sanf.service;

import co.sena.sanf.domain.Competences;
import co.sena.sanf.domain.CompetencesRegister;
import co.sena.sanf.domain.Meta;
import co.sena.sanf.repository.CompetencesRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UnwindOptions;
import io.github.cdimascio.dotenv.Dotenv;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

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

    @Inject MongoClient mongoClient;
    @Inject CompetencesRepository competencesRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public Document getCompetences(String idCompetences) {
        if (ObjectId.isValid(idCompetences)) {
            List<Bson> queries = getListQuery(new ObjectId(idCompetences));
            Optional<Document> doc = getCollection().aggregate(queries).allowDiskUse(true)
                .into(new ArrayList<>()).stream().findFirst();
            return doc.orElseThrow();
        }
        return null;
    }

    public void addCompetencesList(Competences competences) throws UnknownHostException {
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
            .data(competences)
            .build();
        competencesRepository.persist(register);
    }
    private List<Bson> getListQuery(ObjectId idCompetences) {
        Bson stage_0_match = match(eq("_id", idCompetences));
        Bson stage_1_lookup = lookup("SENA_PROG-RESULTS", "data.resultados", "_id", "results");
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

        return pipeline;
    }
    private MongoCollection<Document> getCollection (){
        Dotenv dotenv = Dotenv.configure().load();
        MongoDatabase db = mongoClient.getDatabase(dotenv.get("DATABASE"));
        return db.getCollection(dotenv.get("COLLECTION_COMPETENCES"));
    }
}
