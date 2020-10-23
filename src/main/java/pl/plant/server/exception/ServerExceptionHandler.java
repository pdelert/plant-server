package pl.plant.server.exception;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

@Slf4j
@Provider
public class ServerExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        String message = exception.getMessage();
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

        String uuid = UUID.randomUUID().toString();
        log.error(String.format("%s - Error: %s, message: %s",
                uuid,
                exception.getClass(),
                message), exception);

        return Response
                .status(statusCode)
                .entity(String.format("Internal server error, id: %s", uuid))
                .build();
    }
}
