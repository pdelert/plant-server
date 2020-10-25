package pl.plant.server.validator;

import pl.plant.server.request.EventListRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidListRequestValidator implements ConstraintValidator<ValidListRequest, EventListRequest> {

    @Override
    public boolean isValid(EventListRequest value, ConstraintValidatorContext context) {
        return value.getStart() < value.getEnd();
    }
}
