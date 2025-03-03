package com.openclassrooms.PayMyBuddy.validators.annotations;

import com.openclassrooms.PayMyBuddy.validators.DescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ValidUsername Annotation
 */
@Constraint(validatedBy = DescriptionValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDescription {

    /**
     * message
     *
     * @return String
     */
    String message() default "La description est obligatoire et doit contenir entre 2 et 45 caractères.";

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
