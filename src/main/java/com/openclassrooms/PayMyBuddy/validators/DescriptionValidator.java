package com.openclassrooms.PayMyBuddy.validators;

import com.openclassrooms.PayMyBuddy.validators.annotations.ValidDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * DescriptionValidator Class
 */
public class DescriptionValidator implements ConstraintValidator<ValidDescription, String> {


    /**
     * Initialize the constraint annotation
     *
     * @param constraintAnnotation the annotation
     */
    @Override
    public void initialize(ValidDescription constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Check if the description is valid
     *
     * @param description                the description
     * @param constraintValidatorContext the context
     * @return true if the description is valid
     */
    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        return description != null &&
                description.length() >= 3 &&
                description.length() <= 45;

    }
}
