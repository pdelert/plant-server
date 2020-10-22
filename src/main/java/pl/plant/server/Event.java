package pl.plant.server;

import lombok.*;

import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Event {

    private UUID id;
    @JsonbProperty("event_type")
    private EventType eventType;
    private LocalDateTime time;

    @JsonbProperty("dry_level")
    private double dryLevel;
    private double threshold;
    @JsonbProperty("raw_value")
    private int rawValue;
}
