package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Setter
@Getter
@ToString
public class FindPlayersRequest {
    @Size(max = 12)
    private String name;
    
    @Size(max = 30)
    private String title;
    private Race race;
    private Profession profession;
    private LocalDate after;
    private LocalDate before;
    private Boolean banned;
    
    @Range(min = 0, max = 10_000_000)
    private Integer maxExperience;
    
    @Range(min = 0, max = 10_000_000)
    private Integer minExperience;
    private Integer maxLevel;
    private Integer minLevel;
    private PlayerOrder order = PlayerOrder.ID;
    private Integer pageNumber = 0;
    private Integer pageSize = 3;
}
