package com.openclassrooms.PayMyBuddy.validators.annotations;

import com.openclassrooms.PayMyBuddy.validators.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ValidUsername Annotation
 */
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
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
