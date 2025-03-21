package com.openclassrooms.PayMyBuddy.validators.annotations;

import com.openclassrooms.PayMyBuddy.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidUsername Annotation
 */
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidUsername {

    /**
     * message
     *
     * @return String
     */
    String message() default "Le username doit comporter entre 3 et 45 caractères (lettres et des chiffres uniquement)";

    /**
     * pattern
     *
     * @return String
     */
    String pattern() default "^[a-zA-Z0-9]{3,45}$";

    /**
     * groups
     *
     * @return Class
     */
    Class<?>[] groups() default {};

    /**
     * payload
     *
     * @return Class
     */
    Class<? extends Payload>[] payload() default {};
}
