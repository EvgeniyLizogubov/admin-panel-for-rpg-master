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
    
    @Min(0)
    @NotNull
    private Long birthday;
    private Boolean banned = false;
    
    @Range(min = 0, max = 10_000_000)
    @NotNull
    private Integer experience;
}
