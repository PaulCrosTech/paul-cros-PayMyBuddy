package com.openclassrooms.PayMyBuddy.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.lang.annotation.*;

/**
 * ValidEmail Annotation
 */
@Constraint(validatedBy = {})
@NotEmpty(message = "L''email est obligatoire.")
@Email(message = "L''email n''est pas valide.")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidEmail {

    String message() default "L''email est obligatoire et doit être valide.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
