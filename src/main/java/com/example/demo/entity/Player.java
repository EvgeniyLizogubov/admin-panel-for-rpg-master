package com.example.demo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "race_id", referencedColumnName = "id")
    private PlayerRace race;
    
    @OneToOne(fetch = FetchType.EAGER)
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
