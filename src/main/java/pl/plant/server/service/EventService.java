package pl.plant.server.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.plant.server.EventRepository;
import pl.plant.server.data.Event;
import pl.plant.server.request.EventRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__(@Inject))
@ApplicationScoped
@Transactional
public class EventService {

    EventRepository eventRepository;
    TimeService timeService;

    public Event createEvent(EventRequest eventRequest, String user, String ip) {
        UUID uuid = UUID.randomUUID();
        Event event = Event
                .builder()
                .id(uuid)
                .dryLevel(eventRequest.getDryLevel().doubleValue())
                .eventType(eventRequest.getEventType())
                .rawValue(eventRequest.getRawValue().intValue())
                .threshold(eventRequest.getThreshold().doubleValue())
                .user(user)
                .ip(ip)
                .time(timeService.getCurrentUtcTime())
                .build();

        event.persist();
        return event;
    }

    public Event findBy(UUID id) {
        return eventRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(String.format("Event with id: %s not found.", id)));
    }

    public List<Event> findBy(LocalDateTime startTime, LocalDateTime endTime) {
        return eventRepository.findBy(startTime, endTime);
    }
}
