package co.sena.sanf.service;

import co.sena.sanf.domain.Meta;
import co.sena.sanf.domain.ResultRegister;
import co.sena.sanf.repository.ResultsRepository;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.types.ObjectId;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ResultService {

    @Inject ResultsRepository resultsRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public List<ResultRegister> getListResults() {
        return resultsRepository.listAll();
    }
    public void addResults(ResultRegister result) throws UnknownHostException {
        ResultRegister register = ResultRegister.builder()
            .meta(
                Meta.builder()
                    .creationDate(LocalDateTime.now())
                    .source(httpServerRequestProvider.get().absoluteURI())
                    .ipAddress(InetAddress.getLocalHost().getHostAddress())
                    .type("Resultados-Competencias-CME")
                    .tokenRaw("Bearer bGciOiJSUzI1NiIsIhPqZCI6I")
                    .build()
            )
            .data(result.getData())
            .build();
        resultsRepository.persist(register);
    }
}
