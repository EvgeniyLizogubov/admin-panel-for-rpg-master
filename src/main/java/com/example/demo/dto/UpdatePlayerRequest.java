package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.util.converter.LongToLocalDateConverter;
import com.example.demo.util.validation.YearRange;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class UpdatePlayerRequest {
    @Size(max = 12)
    private String name;
    
    @Size(max = 30)
    private String title;
    private Race race;
    private Profession profession;
    
    @JsonDeserialize(converter = LongToLocalDateConverter.class)
    @YearRange(min = 2000, max = 3000)
    private LocalDate birthday;
    private Boolean banned;
    
    @Range(min = 0, max = 10_000_000)
    private Integer experience;
}
