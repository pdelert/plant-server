package pl.plant.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventRequest {

    @NotNull
    @JsonbProperty("event_type")
    private EventType eventType;

    @NotNull
    @JsonbProperty("dry_level")
    private double dryLevel;

    @NotNull
    private double threshold;

    @Min(0)
    @Max(100)
    @JsonbProperty("raw_value")
    private int rawValue;
}
