package pl.plant.server;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.config.JsonPathConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.plant.server.data.Event;
import pl.plant.server.data.EventType;
import pl.plant.server.request.EventRequest;
import pl.plant.server.service.EventService;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.HttpHeaders;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class EventControllerTest {

    @InjectMock
    EventService eventService;

    @Inject
    JwtTokenService jwtTokenService;

    @Test
    public void shouldReturnNotFound() {
        Mockito.when(eventService.findBy(Mockito.any(UUID.class))).thenThrow(new NotFoundException());

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("read:events")))
                .contentType(ContentType.JSON)
                .when().get("/plant/server/events/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    public void shouldReturnEvent() {
        Event event = Event.builder().id(UUID.randomUUID()).build();
        Mockito.when(eventService.findBy(event.getId())).thenReturn(event);

        JsonConfig jsonConfig = JsonConfig.jsonConfig()
                .numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE);
        given()
                .config(RestAssuredConfig.config().jsonConfig(jsonConfig))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("read:events")))
                .contentType(ContentType.JSON)
                .when().get("/plant/server/events/" + event.getId())
                .then()
                .statusCode(200)
                .body("dry_level", Matchers.equalTo((event.getDryLevel())))
                .body("threshold", Matchers.equalTo(event.getThreshold()))
                .body("raw_value", Matchers.equalTo(event.getRawValue()));
    }

    @Test
    public void shouldReturnEventsList() {
        Event event = Event.builder().id(UUID.randomUUID()).build();
        Mockito.when(eventService.findBy(Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(event));

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("read:events")))
                .contentType(ContentType.JSON)
                .when().get("/plant/server/events?start={start}&end={end}", 1603486676, 1603573076)
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldThrowBadRequestWhenRequestingList() {
        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("read:events")))
                .contentType(ContentType.JSON)
                .when().get("/plant/server/events?start={start}&end={end}", 1603573076, 1603486676)
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldThrowBadRequest() {
        EventRequest eventRequest = EventRequest
                .builder()
                .build();
        given()
                .when()
                .with()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("write:events")))
                .contentType(ContentType.JSON)
                .body(eventRequest, ObjectMapperType.JSONB)
                .post("/plant/server/events/")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldThrowForbidden() {
        EventRequest eventRequest = EventRequest
                .builder()
                .build();
        given()
                .when()
                .with()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("read:events")))
                .contentType(ContentType.JSON)
                .body(eventRequest, ObjectMapperType.JSONB)
                .post("/plant/server/events/")
                .then()
                .statusCode(403);
    }

    @Test
    public void shouldCreateEvent() {
        EventRequest eventRequest = EventRequest
                .builder()
                .dryLevel(BigDecimal.ONE)
                .eventType(EventType.CHECK)
                .rawValue(BigInteger.ONE)
                .threshold(BigDecimal.TEN)
                .build();
        Event event = Event.builder().id(UUID.randomUUID()).build();
        Mockito.when(eventService.createEvent(Mockito.any(EventRequest.class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(event);

        given()
                .when()
                .with()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenService.createToken("pi", 30, Set.of("write:events")))
                .contentType(ContentType.JSON)
                .body(eventRequest, ObjectMapperType.JSONB)
                .post("/plant/server/events/")
                .then()
                .statusCode(201)
                .header(HttpHeaders.LOCATION, "http://localhost:8081/plant/server/events/" + event.getId());
    }
}