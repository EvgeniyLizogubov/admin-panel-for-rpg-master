package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.util.ToLocalDateConverter;
import com.example.demo.util.validation.YearRange;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
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
    
    @JsonDeserialize(converter = ToLocalDateConverter.class)
    @YearRange(min = 2000, max = 3000)
    private LocalDate birthday;
    private Boolean banned;
    
    @Range(min = 0, max = 10_000_000)
    private Integer experience;
}
