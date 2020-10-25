package pl.plant.server.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidListRequestValidator.class)
public @interface ValidListRequest {

    String message() default "Time window must be valid!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
