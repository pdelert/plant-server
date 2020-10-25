package pl.plant.server.response;

import pl.plant.server.data.EventType;

import javax.json.bind.annotation.JsonbProperty;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventDto {

    @JsonbProperty("event_id")
    private UUID id;
    @JsonbProperty("event_type")
    private EventType eventType;
    private LocalDateTime time;
    private String user;
    private String ip;
    @JsonbProperty("dry_level")
    private double dryLevel;
    private double threshold;
    @JsonbProperty("raw_value")
    private int rawValue;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getDryLevel() {
        return dryLevel;
    }

    public void setDryLevel(double dryLevel) {
        this.dryLevel = dryLevel;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public int getRawValue() {
        return rawValue;
    }

    public void setRawValue(int rawValue) {
        this.rawValue = rawValue;
    }
}
