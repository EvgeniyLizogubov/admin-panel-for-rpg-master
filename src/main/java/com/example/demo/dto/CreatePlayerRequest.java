package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.util.LongToLocalDateConverter;
import com.example.demo.util.validation.YearRange;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CreatePlayerRequest {
    @NotBlank
    @Size(max = 12)
    private String name;
    
    @Size(max = 30)
    @NotEmpty
    private String title;
    
    @NotNull
    private Race race;
    
    @NotNull
    private Profession profession;
    
    @NotNull
    @JsonDeserialize(converter = LongToLocalDateConverter.class)
    @YearRange(min = 2000, max = 3000)
    private LocalDate birthday;
    private Boolean banned = false;
    
    @Range(min = 0, max = 10_000_000)
    @NotNull
    private Integer experience;
}
