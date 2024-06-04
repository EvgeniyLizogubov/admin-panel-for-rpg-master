package com.example.demo.dto;

import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import com.example.demo.util.validation.DateYearRange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;
import java.util.Date;

@Setter
@Getter
@ToString
public class RequestSearchingDTO {
    @Size(max = 12)
    private String name;
    
    @Size(max = 30)
    private String title;
    private Race race;
    private Profession profession;
    
    @DateYearRange
    private Date after;
    
    @DateYearRange
    private Date before;
    private Boolean banned = false;
    
    @Range(min = 0, max = 10_000_000)
    private Integer maxExperience;
    
    @Range(min = 0, max = 10_000_000)
    private Integer minExperience;
    
    private Integer maxLevel;
    private Integer minLevel;
    private PlayerOrder playerOrder = PlayerOrder.ID;
    private Integer pageNumber = 0;
    private Integer pageSize = 3;
}
