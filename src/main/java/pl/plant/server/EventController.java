package pl.plant.server;

import org.jboss.resteasy.annotations.GZIP;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.UUID;

@Path("/server/event")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@GZIP
public class EventController {

    @GET
    @Path("/{id}")
    public Event getEvent(@PathParam("id") UUID id) {
        return new Event();
    }

    @POST
    public Response createEvent(Event event) {
        UUID uuid = UUID.randomUUID();

        return Response
                .created(UriBuilder
                        .fromResource(EventController.class)
                        .path(uuid.toString())
                        .build())
                .build();
    }
}