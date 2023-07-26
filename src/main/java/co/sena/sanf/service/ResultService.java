package co.sena.sanf.service;

import co.sena.sanf.domain.Meta;
import co.sena.sanf.domain.result.ResultRegister;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.repository.ResultsRepository;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ResultService {

    @Inject Logger LOG;
    @Inject ResultsRepository resultsRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public List<ResultRegister> getListResults() throws DRException {
        LOG.info("@getListResults API > Inicia servicio de obtención de listado de resultados");
        List<ResultRegister> results = resultsRepository.listAll();
        isListEmpty(results);
        LOG.info("@getListResults API > Finaliza servicio de obtención de listado de resultados");
        return results;
    }

    public void addResults(ResultRegister result) throws UnknownHostException {
        LOG.infof("@addResults API > Inicia servicio para agregar los resultados de una competencia con" +
            " data: %s", result);
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

        LOG.infof("@addResults API > La lista de resultados fueron agregados exitosamente");
        LOG.infof("@addResults API > Finaliza servicio para agregar los resultados de una competencia con" +
            " data: %s", result);
    }

    private void isListEmpty(List<ResultRegister> results) throws DRException {
        if (results.isEmpty()) {
            LOG.info("@isListEmpty API > No se encontraron registros de resultados en la base de datos");
            throw new DRException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron registros de " +
                "resultados en la base de datos");
        }
        LOG.info("@isListEmpty API > El listado de resultados se obtuvo exitosamente");
    }
}
