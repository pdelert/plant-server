package pl.plant.server.mapper;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.plant.server.data.Event;
import pl.plant.server.data.EventType;
import pl.plant.server.response.EventDto;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

@QuarkusTest
class EventMapperTest {

    @Inject
    EventMapper eventMapper;

    @Test
    void shouldMapEvent() {
        Event event = Event
                .builder()
                .id(UUID.randomUUID())
                .dryLevel(10.0)
                .eventType(EventType.CHECK)
                .ip("ip")
                .rawValue(122)
                .threshold(40.0)
                .time(LocalDateTime.of(2020, 10, 25, 15, 33))
                .user("user")
                .build();

        EventDto eventDto = eventMapper.map(event);

        Assertions.assertEquals(event.getDryLevel(), eventDto.getDryLevel());
        Assertions.assertEquals(event.getEventType(), eventDto.getEventType());
        Assertions.assertEquals(event.getRawValue(), eventDto.getRawValue());
        Assertions.assertEquals(event.getId(), eventDto.getId());
        Assertions.assertEquals(event.getIp(), eventDto.getIp());
        Assertions.assertEquals(event.getUser(), eventDto.getUser());
    }
}