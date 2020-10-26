package annotations;

import annotations.validators.AuthorizeValidator;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Constraint(validatedBy = AuthorizeValidator.class)
public @interface Authorize {
}