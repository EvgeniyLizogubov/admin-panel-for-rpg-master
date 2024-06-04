package com.example.demo.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateValidator implements ConstraintValidator<DateYearRange, Date> {
    @Override
    public void initialize(DateYearRange constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        return value.getYear() >= 2000 && value.getYear() <= 3000;
    }
}
