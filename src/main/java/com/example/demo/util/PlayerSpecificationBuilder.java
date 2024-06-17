package com.example.demo.util;

import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.entity.Player;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecificationBuilder {
    private final FindPlayersRequest request;
    
    public PlayerSpecificationBuilder(FindPlayersRequest request) {
        this.request = request;
    }
    
    public Specification<Player> getSpecification() {
        Specification<Player> specification = Specification.where(null);
        
        if (request.getName() != null) {
            specification = specification.and(new PlayerSpecification("name", request.getName()));
        }
        
        if (request.getTitle() != null) {
            specification = specification.and(new PlayerSpecification("title", request.getTitle()));
        }
        
        if (request.getRace() != null) {
            specification = specification.and(new PlayerSpecification("race", request.getRace()));
        }
        
        if (request.getProfession() != null) {
            specification = specification.and(new PlayerSpecification("profession", request.getProfession()));
        }
        
        if (request.getBefore() != null) {
            specification = specification.and(new PlayerSpecification("before", request.getBefore()));
        }
        
        if (request.getAfter() != null) {
            specification = specification.and(new PlayerSpecification("after", request.getAfter()));
        }
        
        if (request.getMinExperience() != null) {
            specification = specification.and(new PlayerSpecification("minExperience", request.getMinExperience()));
        }
        
        if (request.getMaxExperience() != null) {
            specification = specification.and(new PlayerSpecification("maxExperience", request.getMaxExperience()));
        }
        
        if (request.getMinLevel() != null) {
            specification = specification.and(new PlayerSpecification("minLevel", request.getMinLevel()));
        }
        
        if (request.getMaxLevel() != null) {
            specification = specification.and(new PlayerSpecification("maxLevel", request.getMaxLevel()));
        }
        
        if (request.getBanned() != null) {
            specification = specification.and(new PlayerSpecification("banned", request.getBanned()));
        }
        
        return specification;
    }
}
