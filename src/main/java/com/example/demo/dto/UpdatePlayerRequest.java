package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

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
    
    @Min(0)
    private Long birthday;
    private Boolean banned;
    
    @Range(min = 0, max = 10_000_000)
    private Integer experience;
}
