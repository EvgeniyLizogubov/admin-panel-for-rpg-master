package com.example.demo.util.validation;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    private PlayerRepository repository;
    
    public UniqueNameValidator(PlayerRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public void initialize(UniqueName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Player player = repository.getPlayerByName(value);
        return player == null;
    }
}
