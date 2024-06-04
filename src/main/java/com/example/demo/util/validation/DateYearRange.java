package com.example.demo.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
public @interface DateYearRange {
    String message() default "Date is not in range.";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
}
