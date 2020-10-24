package pl.plant.server;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import pl.plant.server.data.Event;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, UUID> {

    public List<Event> findBy(LocalDateTime start, LocalDateTime end) {
        return find("from Event where time > :start and time < :end order by time asc",
                Parameters.with("start", start)
                        .and("end", end))
                .list();
    }
}
