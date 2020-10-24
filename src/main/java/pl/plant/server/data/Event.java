package pl.plant.server.data;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "events")
public class Event extends PanacheEntityBase {

    @Id
    @Column(name = "event_id")
    private UUID id;
    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "user_name")
    private String user;
    @Column(name = "ip")
    private String ip;
    @Column(name = "dry_level")
    private double dryLevel;
    @Column(name = "threshold")
    private double threshold;
    @Column(name = "raw_value")
    private int rawValue;
}
