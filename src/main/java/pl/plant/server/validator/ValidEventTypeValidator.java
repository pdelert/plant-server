package pl.plant.server.validator;

import pl.plant.server.data.EventType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValidEventTypeValidator implements ConstraintValidator<ValidEventType, EventType> {

    private EventType[] subset;

    @Override
    public void initialize(ValidEventType constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(EventType eventType, ConstraintValidatorContext constraintValidatorContext) {
        return eventType == null || Arrays.asList(subset).contains(eventType);
    }
}
