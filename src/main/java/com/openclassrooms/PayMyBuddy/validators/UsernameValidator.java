package com.openclassrooms.PayMyBuddy.validators;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * UsernameValidator Class
 */
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private String pattern;

    /**
     * Initialize the pattern
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    /**
     * Check if the username is valid
     *
     * @param userName                   the username
     * @param constraintValidatorContext the context
     * @return true if the username is valid
     */
    @Override
    public boolean isValid(String userName, ConstraintValidatorContext constraintValidatorContext) {
        return userName != null && userName.matches(pattern);
    }
}
