package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PlayerDTO {
    private Long id;
    
    private String name;
    
    private String title;
    
    private Race race;
    
    private Profession profession;
    
    private LocalDate birthday;
    
    private Boolean banned;
    
    private Integer experience;
    
    private Integer level;
    
    private Integer untilNextLevel;
}
