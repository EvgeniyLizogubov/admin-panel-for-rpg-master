package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Player {
    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;
    private LocalDate birthday;
    private Boolean banned;
}
