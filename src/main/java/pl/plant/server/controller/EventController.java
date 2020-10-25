package pl.plant.server.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.spi.HttpRequest;
import pl.plant.server.data.Event;
import pl.plant.server.mapper.EventMapper;
import pl.plant.server.request.EventListRequest;
import pl.plant.server.request.EventRequest;
import pl.plant.server.response.EventDto;
import pl.plant.server.service.EventService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.resource.spi.SecurityPermission;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
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
import java.time.Instant;
import java.time.LocalDateTime;
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
@ApplicationScoped
public class EventController {

    EventService eventService;
    EventMapper eventMapper;

    @GET
    @Path("/{id}")
    @RolesAllowed("read:events")
    public EventDto getEvent(@PathParam("id") UUID id) {
        log.info("Looking for event: {}", id);
        return eventMapper.map(eventService.findBy(id));
    }

    @GET
    @RolesAllowed("read:events")
    public List<EventDto> getEvents(@Valid @BeanParam EventListRequest eventListRequest) {
        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(eventListRequest.getStart()), ZoneOffset.UTC);
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(eventListRequest.getEnd()), ZoneOffset.UTC);
        log.info("Looking for events in time frame: {} - {}", startTime, endTime);
        return eventMapper.map(eventService.findBy(startTime, endTime));
    }

    @POST
    @RolesAllowed("write:events")
    public Response createEvent(@Valid EventRequest eventRequest, @Context SecurityContext ctx, @Context HttpRequest httpRequest) {
        String user = Optional.ofNullable(ctx.getUserPrincipal())
                .map(Principal::getName)
                .orElse("Anonymous");

        log.info("Received event from {}, ip: {}", user, httpRequest.getRemoteAddress());

        Event event = eventService.createEvent(eventRequest, user, httpRequest.getRemoteAddress());

        return Response
                .created(UriBuilder
                        .fromResource(EventController.class)
                        .path(event.getId().toString())
                        .build())
                .build();
    }
}