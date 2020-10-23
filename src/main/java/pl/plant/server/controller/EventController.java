package pl.plant.server.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.spi.HttpRequest;
import pl.plant.server.data.Event;
import pl.plant.server.service.TimeService;
import pl.plant.server.request.EventRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Path("/server/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Inject))
@GZIP
@Slf4j
@Transactional
@ApplicationScoped
public class EventController {

    TimeService timeService;

    @GET
    @Path("/{id}")
    public Event getEvent(@PathParam("id") UUID id) {
        log.info("Looking for event: {}", id);
        return Event.findById(id);
    }

    @POST
    public Response createEvent(@Valid EventRequest eventRequest, @Context SecurityContext ctx, @Context HttpRequest httpRequest) {
        LocalDateTime currentUtcTime = timeService.getCurrentUtcTime();
        String user = Optional.ofNullable(ctx.getUserPrincipal())
                .map(Principal::getName)
                .orElse("Anonymous");
        log.info("Received heart beat from {}, ip: {}, at: {}",
                user,
                httpRequest.getRemoteAddress(),
                currentUtcTime);
        UUID uuid = UUID.randomUUID();
        Event event = Event
                .builder()
                .id(uuid)
                .dryLevel(eventRequest.getDryLevel().doubleValue())
                .eventType(eventRequest.getEventType())
                .rawValue(eventRequest.getRawValue().intValue())
                .threshold(eventRequest.getThreshold().doubleValue())
                .user(user)
                .ip(httpRequest.getRemoteAddress())
                .time(currentUtcTime)
                .build();

        event.persist();

        return Response
                .created(UriBuilder
                        .fromResource(EventController.class)
                        .path(uuid.toString())
                        .build())
                .build();
    }
}