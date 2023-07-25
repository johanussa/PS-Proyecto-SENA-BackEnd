package co.sena.sanf.service;

import co.sena.sanf.domain.ClassRoom;
import co.sena.sanf.domain.Meta;
import co.sena.sanf.helper.exceptions.DRException;
import co.sena.sanf.repository.ClassRoomRepository;
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
public class ClassRoomService {

    @Inject Logger LOG;
    @Inject ClassRoomRepository classRoomRepository;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    public ClassRoom getListClassRooms() throws DRException {
        LOG.infof("@getListClassRooms API > Inicia obtención de lista de ambientes registrados");
        Optional<ClassRoom> classRoom = classRoomRepository.getList();
        validIsEmpty(classRoom.isPresent());
        return classRoom.orElseThrow();
    }

    public void addClassRooms(ClassRoom classRoom) throws UnknownHostException {
        LOG.infof("@addClassRooms API > Inicia servicio para registo de lista de ambientes en base de datos con " +
            "data: %s", classRoom);
        ClassRoom register = ClassRoom.builder()
            .meta(
                Meta.builder()
                    .creationDate(LocalDateTime.now())
                    .source(httpServerRequestProvider.get().absoluteURI())
                    .ipAddress(InetAddress.getLocalHost().getHostAddress())
                    .type("Ambientes-CME-Complejo-Sur")
                    .tokenRaw("Bearer bGciOiJSUzI1NiIsIhPqZCI6I")
                    .build()
            )
            .data(classRoom.getData())
            .build();
        classRoomRepository.persist(register);
        LOG.infof("@addClassRooms API > El registro de la lista de ambientes fue exitosa");
        LOG.infof("@addClassRooms API > Finaliza servicio para registo de lista de ambientes en base de datos " +
            "con data: %s", classRoom);
    }

    private void validIsEmpty(boolean classRoomState) throws DRException {
        if (classRoomState) return;
        LOG.infof("@validIsEmpty API > No se encontraron registros de ambientes en la base de datos");
        throw new DRException(Response.Status.NOT_FOUND.getStatusCode(),
            "No se encontro lista de ambientes en la base de datos");
    }
}
