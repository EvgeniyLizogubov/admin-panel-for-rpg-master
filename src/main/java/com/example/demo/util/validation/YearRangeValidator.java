package com.example.demo.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class YearRangeValidator implements ConstraintValidator<YearRange, LocalDate> {
    private int min;
    private int max;
    
    @Override
    public void initialize(YearRange constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }
    
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || min <= value.getYear() && value.getYear() <= max;
    }
}
