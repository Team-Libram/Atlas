package annotations.validators;

import annotations.Authorize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AuthorizeValidator implements ConstraintValidator<Authorize, String> {

    @Override
    public void initialize(Authorize constraintAnnotation) {
    }

    @Override
    public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}