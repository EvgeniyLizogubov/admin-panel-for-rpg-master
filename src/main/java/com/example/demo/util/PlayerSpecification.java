package com.example.demo.util;

import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.entity.Player;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.ZoneId;

@RequiredArgsConstructor
public class PlayerSpecification implements Specification<FindPlayersRequest>{
    private final FindPlayersRequest request;
    
    public Specification<Player> getSpecification() {
        Specification<Player> specification = Specification.where(null);
        
        if (request.getName() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
        }
        
        if (request.getTitle() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.like(root.get("title"), "%" + request.getTitle() + "%"));
        }
        
        if (request.getRace() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.join("race").get("race"), request.getRace()));
        }
        
        if (request.getProfession() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.join("profession").get("profession"), request.getProfession()));
        }
        
        if (request.getBefore() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThan(root.get("birthday"),
                    Instant.ofEpochMilli(request.getBefore()).atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        
        if (request.getAfter() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThan(root.get("birthday"),
                    Instant.ofEpochMilli(request.getAfter()).atZone(ZoneId.systemDefault()).toLocalDate()));
        }
        
        if (request.getMinExperience() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), request.getMinExperience()));
        }
        
        if (request.getMaxExperience() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThanOrEqualTo(root.get("experience"), request.getMaxExperience()));
        }
        
        if (request.getMinLevel() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get("level"), request.getMinLevel()));
        }
        
        if (request.getMaxLevel() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThanOrEqualTo(root.get("level"), request.getMaxLevel()));
        }
        
        if (request.getBanned() != null) {
            specification = specification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(root.get("banned"), request.getBanned()));
        }
        
        return specification;
    }
    
    @Override
    public Predicate toPredicate(Root<FindPlayersRequest> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//        if (request.getName() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
//        }
//
//        if (request.getTitle() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.like(root.get("title"), "%" + request.getTitle() + "%"));
//        }
//
//        if (request.getRace() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.equal(root.join("race").get("race"), request.getRace()));
//        }
//
//        if (request.getProfession() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.equal(root.join("profession").get("profession"), request.getProfession()));
//        }
//
//        if (request.getBefore() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.lessThan(root.get("birthday"),
//                    Instant.ofEpochMilli(request.getBefore()).atZone(ZoneId.systemDefault()).toLocalDate()));
//        }
//
//        if (request.getAfter() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.greaterThan(root.get("birthday"),
//                    Instant.ofEpochMilli(request.getAfter()).atZone(ZoneId.systemDefault()).toLocalDate()));
//        }
//
//        if (request.getMinExperience() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), request.getMinExperience()));
//        }
//
//        if (request.getMaxExperience() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.lessThanOrEqualTo(root.get("experience"), request.getMaxExperience()));
//        }
//
//        if (request.getMinLevel() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.greaterThanOrEqualTo(root.get("level"), request.getMinLevel()));
//        }
//
//        if (request.getMaxLevel() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.lessThanOrEqualTo(root.get("level"), request.getMaxLevel()));
//        }
//
//        if (request.getBanned() != null) {
//            specification = specification.and((root, query, criteriaBuilder)
//                    -> criteriaBuilder.equal(root.get("banned"), request.getBanned()));
//        }
        return null;
    }
}
