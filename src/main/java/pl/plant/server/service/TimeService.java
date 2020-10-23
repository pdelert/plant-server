package pl.plant.server.service;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ApplicationScoped
public class TimeService {

    public LocalDateTime getCurrentUtcTime() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}
