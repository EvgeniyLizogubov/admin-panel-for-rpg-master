package com.example.demo.util;

import com.example.demo.entity.Player;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.ZoneId;

@AllArgsConstructor
public class PlayerSpecification implements Specification<Player> {
    private String key;
    private Object value;
    
    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return switch (key) {
            case "name", "title" -> criteriaBuilder.like(root.get(key), "%" + value + "%");
            case "race", "profession", "banned" -> criteriaBuilder.equal(root.get(key), value);
            case "before" -> criteriaBuilder
                    .lessThan(root.get("birthday"),
                            Instant.ofEpochMilli((long) value).atZone(ZoneId.systemDefault()).toLocalDate());
            case "after" -> criteriaBuilder
                    .greaterThan(root.get("birthday"),
                            Instant.ofEpochMilli((long) value).atZone(ZoneId.systemDefault()).toLocalDate());
            case "minExperience" -> criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), (int) value);
            case "maxExperience" -> criteriaBuilder.lessThanOrEqualTo(root.get("experience"), (int) value);
            case "minLevel" -> criteriaBuilder.greaterThanOrEqualTo(root.get("level"), (int) value);
            case "maxLevel" -> criteriaBuilder.lessThanOrEqualTo(root.get("level"), (int) value);
            default -> null;
        };
    }
}
