package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "player")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", length = 12)
    private String name;
    
    @Column(name = "title", length = 30)
    private String title;
    
    @Column(name = "race", length = 20)
    @Enumerated(EnumType.STRING)
//    @OneToOne
//    @JoinColumn(name = "race_id", referencedColumnName = "raceId")
//    @Convert(converter = Race.TypeConverter.class)
    private Race race;
    
    @Column(name = "profession", length = 20)
    @Enumerated(EnumType.STRING)
    private Profession profession;
    
    @Column(name = "birthday")
    private LocalDate birthday;
    
    @Column(name = "banned")
    private Boolean banned;
    
    @Column(name = "experience", length = 10)
    private Integer experience;
    
    @Column(name = "level", length = 3)
    private Integer level;
    
    @Column(name = "until_next_level", length = 10)
    private Integer untilNextLevel;
}
