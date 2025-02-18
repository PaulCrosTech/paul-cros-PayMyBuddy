package com.openclassrooms.PayMyBuddy.validators;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * PasswordValidator Class
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private String pattern;

    /**
     * Initialize the pattern
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    /**
     * Check if the password is valid
     *
     * @param password the password
     * @param context  the context
     * @return true if the password is valid
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && password.matches(pattern);
    }

}
