package pl.plant.server.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "events")
public class Event extends PanacheEntityBase {

    @Id
    @Column(name = "event_id")
    public UUID id;
    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    public EventType eventType;
    @Column(name = "time")
    public LocalDateTime time;
    @Column(name = "user_name")
    public String user;
    @Column(name = "ip")
    public String ip;
    @Column(name = "dry_level")
    public double dryLevel;
    @Column(name = "threshold")
    public double threshold;
    @Column(name = "raw_value")
    public int rawValue;
}
