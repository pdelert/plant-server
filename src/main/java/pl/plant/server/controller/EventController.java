package pl.plant.server.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.spi.HttpRequest;
import pl.plant.server.EventRepository;
import pl.plant.server.data.Event;
import pl.plant.server.request.EventRequest;
import pl.plant.server.service.TimeService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
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
    EventRepository eventRepository;

    @GET
    @Path("/{id}")
    public Event getEvent(@PathParam("id") UUID id) {
        log.info("Looking for event: {}", id);
        return eventRepository.findById(id);
    }

    @GET
    public List<Event> getEvents(@QueryParam("start") long start, @QueryParam("end") long end) {
        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(start), ZoneOffset.UTC);
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(end), ZoneOffset.UTC);
        log.info("Looking for events in time frame: {} - {}", startTime, endTime);
        return eventRepository.findBy(startTime, endTime);
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