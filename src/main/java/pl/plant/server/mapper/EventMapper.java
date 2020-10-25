package pl.plant.server.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import pl.plant.server.data.Event;
import pl.plant.server.response.EventDto;

import java.util.List;

@Mapper(componentModel = "cdi",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EventMapper {

    EventDto map(Event event);

    List<EventDto> map(List<Event> event);
}
