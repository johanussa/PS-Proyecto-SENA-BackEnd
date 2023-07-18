package co.sena.sanf.helper.exceptions;

import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.logging.Logger;

import java.util.UUID;

@Provider
public class HandlerException implements ExceptionMapper<Exception> {

    @Inject Logger LOG;
    @Inject jakarta.inject.Provider<HttpServerRequest> httpServerRequestProvider;

    @Override
    public Response toResponse(Exception exception) {
        return mapExceptionToResponseDR(exception);
    }

    private Response mapExceptionToResponseDR(Exception exception) {
        if (exception instanceof DRException ex) {
            ProblemException problemException = ProblemException.builder()
                .host(httpServerRequestProvider.get().host())
                .title(ex.getStatus().getReasonPhrase())
                .detail(ex.getMessage())
                .uri(httpServerRequestProvider.get().absoluteURI())
                .build();
            return Response.status(ex.getStatus()).entity(problemException).build();
        } else {
            UUID idError = UUID.randomUUID();
            LOG.errorf(exception, "@mapExceptionToResponseDR - Failed to process, " +
                "idError: %s - request to: %s", idError.toString(), httpServerRequestProvider.get().absoluteURI());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ResponseError(
                    String.valueOf(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()),
                    idError.toString(),
                    "Internal Server Error"
                )).build();
        }
    }

    public record ResponseError(
        @Schema(example = "500") String code,
        @Schema(example = "ef156aec-3d78-4f78-9b69-1dd8154f993d") String idError,
        @Schema(example = "Internal Server Error") String message
    ) {  }
}
