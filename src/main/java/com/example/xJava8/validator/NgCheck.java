package com.example.xJava8.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NgCheckValidator.class)
public @interface NgCheck {
    String message() default "NGワードが含まれています。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
