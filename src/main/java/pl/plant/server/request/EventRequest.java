package pl.plant.server.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.plant.server.data.EventType;
import pl.plant.server.validator.ValidEventType;

import javax.json.bind.annotation.JsonbProperty;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventRequest {

    @NotNull
    @ValidEventType(anyOf = {EventType.CHECK, EventType.WATERING_ON, EventType.WATERING_OFF})
    @JsonbProperty("event_type")
    private EventType eventType;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @JsonbProperty("dry_level")
    private BigDecimal dryLevel;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal threshold;

    @NotNull
    @Min(0)
    @Max(1000)
    @JsonbProperty("raw_value")
    private BigInteger rawValue;
}
