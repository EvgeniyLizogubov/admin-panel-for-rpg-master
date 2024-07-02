package com.example.demo.util;

import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.entity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PlayerSpecification implements Specification<Player> {
    private final FindPlayersRequest request;
    
    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        
        if (request.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
        }
        
        if (request.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + request.getTitle() + "%"));
        }
        
        if (request.getRace() != null) {
            predicates.add(criteriaBuilder.equal(root.join("race").get("race"), request.getRace()));
        }
        
        if (request.getProfession() != null) {
            predicates.add(criteriaBuilder.equal(root.join("profession").get("profession"), request.getProfession()));
        }
        
        if (request.getBefore() != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("birthday"), request.getBefore()));
        }
        
        if (request.getAfter() != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("birthday"), request.getAfter()));
        }
        
        if (request.getMinExperience() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), request.getMinExperience()));
        }
        
        if (request.getMaxExperience() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("experience"), request.getMaxExperience()));
        }
        
        if (request.getMinLevel() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("level"), request.getMinLevel()));
        }
        
        if (request.getMaxLevel() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("level"), request.getMaxLevel()));
        }
        
        if (request.getBanned() != null) {
            predicates.add(criteriaBuilder.equal(root.get("banned"), request.getBanned()));
        }
        
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
