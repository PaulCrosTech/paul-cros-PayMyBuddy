package com.openclassrooms.PayMyBuddy.validators.annotations;

import com.openclassrooms.PayMyBuddy.validators.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ValidPassword Annotation
 */
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPassword {

    /**
     * message
     *
     * @return String
     */
    String message() default "Le mot de passe doit comporter entre 8 et 20 caractères. Il doit contenir au moins un chiffre, une lettre minuscule, une lettre majuscule, un caractère spécial (@#$%^&+=) et ne doit pas contenir d''espaces.";


    /**
     * pattern
     *
     * @return String
     */
    String pattern() default "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

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
