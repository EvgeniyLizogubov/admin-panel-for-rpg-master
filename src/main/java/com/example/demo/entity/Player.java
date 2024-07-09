package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    
    @Column(name = "name", length = 12, unique = true)
    private String name;
    
    @Column(name = "title", length = 30)
    private String title;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private PlayerRace race;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profession_id", referencedColumnName = "id")
    private PlayerProfession profession;
    
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
