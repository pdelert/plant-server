package pl.plant.server;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class EventControllerTest {

    @Test
    public void initTest() {
        given()
                .when().get("/plant/server/events/" + UUID.randomUUID())
                .then()
                .statusCode(204);
    }

}