package com.shihabmahamud.eshoppers.util;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtil {
    private static final ValidationUtil INSTANCE = new ValidationUtil();
    private final Validator validator;

    public ValidationUtil() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static ValidationUtil getInstance() {
        return INSTANCE;
    }

    public <T> Map<String, String> validate(T object) {
        var violations = validator.validate(object);

        return violations.stream()
                .collect(Collectors.toMap(
                        violation
                                -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage,
                        (errorMsg1, errorMsg2) ->
                                errorMsg1 + "<br/>" + errorMsg2)
                );
    }
}
