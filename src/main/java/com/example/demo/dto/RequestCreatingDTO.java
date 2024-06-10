package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class RequestCreatingDTO {
    private final static long MIN_DATE = 946670400000L; // 2000-01-01
    private final static long MAX_DATE = 32503662000000L; // 3000-01-01
    
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
    @Range(min = MIN_DATE, max = MAX_DATE)
    private Long birthday;
    private Boolean banned = false;
    
    @Range(min = 0, max = 10_000_000)
    @NotNull
    private Integer experience;
}
