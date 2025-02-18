package com.openclassrooms.PayMyBuddy.validators;

import com.openclassrooms.PayMyBuddy.model.dto.DBUserRegisterDto;
import com.openclassrooms.PayMyBuddy.validators.annotations.ValidPasswordMatches;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * PasswordMatchesValidator Class
 */
@Slf4j
public class PasswordMatchesValidator implements ConstraintValidator<ValidPasswordMatches, Object> {

    @Override
    public void initialize(ValidPasswordMatches constraintAnnotation) {
    }

    /**
     * Check if the password and the confirm password are the same
     *
     * @param obj     the object DBUserRegisterDto
     * @param context the context
     * @return true if the password and the confirm password are the same
     */
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        DBUserRegisterDto user = (DBUserRegisterDto) obj;
        if (user.getConfirmPassword() == null || user.getPassword() == null) {
            return false;
        }
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
