package co.sena.sanf.service;

import co.sena.sanf.domain.Meta;
import co.sena.sanf.domain.program.ProgramRegister;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.repository.ProgramRepository;
import io.quarkus.panache.common.Sort;
import io.vertx.core.http.HttpServerRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class ProgramService {

    @Inject Logger LOG;
    @Inject ProgramRepository programRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public ProgramRegister getPrograms() throws DRException {
        LOG.infof("@getPrograms API > Inicia servicio para obtener lista de programas registrados");
        Optional<ProgramRegister> programs = programRepository.findAll(
            Sort.descending("meta.fechaCreacion")
        ).firstResultOptional();
        validListPrograms(programs.isPresent());

        LOG.infof("@getPrograms API > La lista de programas se obtuvo exitosamente");
        LOG.infof("@getPrograms API > Finaliza servicio para obtener lista de programas registrados");
        return programs.orElseThrow();
    }

    public void addPrograms(ProgramRegister programRegister) throws UnknownHostException {
        LOG.infof("@addPrograms API > Inicia servicio para registrar una lista de programas");
        ProgramRegister register = ProgramRegister.builder()
            .meta(
                Meta.builder()
                    .creationDate(LocalDateTime.now())
                    .source(httpServerRequestProvider.get().absoluteURI())
                    .ipAddress(InetAddress.getLocalHost().getHostAddress())
                    .type("Programas-CME-Complejo-Sur")
                    .tokenRaw("Bearer bGciOiJSUzI1NiIsIhPqZCI6I")
                    .build()
            )
            .data(programRegister.getData())
            .build();
        programRepository.persist(register);
        LOG.infof("@addPrograms API > La lista de programas se registró exitosamente");
        LOG.infof("@addPrograms API > Finaliza servicio para registrar una lista de programas");
    }

    private void validListPrograms(boolean programRegister) throws DRException {
        if (programRegister) return;
        LOG.infof("@validListPrograms, No hay lista de programas registrados");
        throw new DRException(Response.Status.NOT_FOUND.getStatusCode(),"No hay lista de programas registrados");
    }
}
