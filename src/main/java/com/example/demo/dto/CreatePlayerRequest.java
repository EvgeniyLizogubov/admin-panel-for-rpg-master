package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.util.converter.LocalDateToLongConverter;
import com.example.demo.util.converter.LongToLocalDateConverter;
import com.example.demo.util.validation.YearRange;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonSerialize(converter = LocalDateToLongConverter.class)
    @YearRange(min = 2000, max = 3000)
    private LocalDate birthday;
    private Boolean banned = false;
    
    @Range(min = 0, max = 10_000_000)
    @NotNull
    private Integer experience;
}
