package co.sena.sanf.helper.exceptions;

import jakarta.ws.rs.core.Response;

public class DRException extends Exception {

    private final Response.Status status;

    public DRException(int statusCode, String message) {
        super(message);
        this.status = Response.Status.fromStatusCode(statusCode);
    }
    public Response.Status getStatus() { return this.status; }
}
