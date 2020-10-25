package pl.plant.server.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.plant.server.validator.ValidListRequest;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ValidListRequest
public class EventListRequest {

    @NotNull
    @QueryParam("start")
    Long start;

    @NotNull
    @QueryParam("end")
    Long end;
}
