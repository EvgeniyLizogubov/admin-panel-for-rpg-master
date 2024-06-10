package com.example.demo.service;

import com.example.demo.dto.RequestCreatingDTO;
import com.example.demo.dto.RequestSearchingDTO;
import com.example.demo.dto.RequestUpdatingDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.Player;
import com.example.demo.error.NotFoundException;
import com.example.demo.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final ModelMapper mapper;
    private final EntityManager entityManager;
    
    public List<ResponseDTO> getAll(RequestSearchingDTO searchingDTO) {
        Specification<Player> specification = getSpecification(searchingDTO);
        PageRequest request = PageRequest.of(
                searchingDTO.getPageNumber(),
                searchingDTO.getPageSize(),
                Sort.Direction.ASC,
                searchingDTO.getPlayerOrder().getFieldName()
        );
        
        Page<Player> players = repository.findAll(specification, request);
        List<ResponseDTO> result = new ArrayList<>();
        players.forEach(player -> result.add(mapper.map(player, ResponseDTO.class)));
        return result;
    }
    
    public int getCount(RequestSearchingDTO searchingDTO) {
        Specification<Player> specification = getSpecification(searchingDTO);
        return (int) repository.count(specification);
    }
    
    public ResponseDTO get(long id) {
        Player player = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        return mapper.map(player, ResponseDTO.class);
    }
    
    public ResponseDTO create(RequestCreatingDTO creatingDTO) {
        Player playerToCreate = mapper.map(creatingDTO, Player.class);
        Player createdPlayer = repository.save(playerToCreate);
        return mapper.map(createdPlayer, ResponseDTO.class);
    }
    
    public ResponseDTO update(long id, RequestUpdatingDTO updatingDTO) {
        Player playerToUpdate = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        mapper.map(updatingDTO, playerToUpdate);
        return mapper.map(repository.save(playerToUpdate), ResponseDTO.class);
    }
    
    public void delete(long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
        repository.deleteById(id);
    }
    
    private Specification<Player> getSpecification(RequestSearchingDTO searchingDTO) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> cq = cb.createQuery(Player.class);
        Root<Player> playerRoot = cq.from(Player.class);
        Specification<Player> specification = where(null);
        
        if (searchingDTO.getName() != null) {
            Specification<Player> nameLike =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get("name"), "%" + searchingDTO.getName() + "%");
            
            specification = specification.and(nameLike);
        }
        
        if (searchingDTO.getTitle() != null) {
            Specification<Player> titleLike =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.like(root.get("title"), "%" + searchingDTO.getTitle() + "%");
            
            specification = specification.and(titleLike);
        }
        
        if (searchingDTO.getRace() != null) {
            Specification<Player> raceEqual =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("race"), searchingDTO.getRace());
            
            specification = specification.and(raceEqual);
        }
        
        if (searchingDTO.getProfession() != null) {
            Specification<Player> professionEqual =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("profession"), searchingDTO.getProfession());
            
            specification = specification.and(professionEqual);
        }
        
        if (searchingDTO.getBefore() != null) {
            Specification<Player> birthdayLessThan =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThan(root.<Date>get("birthday"), new Date(searchingDTO.getBefore()));
            
            specification = specification.and(birthdayLessThan);
        }
        
        if (searchingDTO.getAfter() != null) {
            Specification<Player> birthdayGreaterThan =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThan(root.<Date>get("birthday"), new Date(searchingDTO.getAfter()));
            
            specification = specification.and(birthdayGreaterThan);
        }
        
        if (searchingDTO.getMinExperience() != null) {
            Specification<Player> experienceGreaterThanOfEqualTo =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), searchingDTO.getMinExperience());
            
            specification = specification.and(experienceGreaterThanOfEqualTo);
        }
        
        if (searchingDTO.getMaxExperience() != null) {
            Specification<Player> experienceLessThanOfEqualTo =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("experience"), searchingDTO.getMaxExperience());
            
            specification = specification.and(experienceLessThanOfEqualTo);
        }
        
        if (searchingDTO.getMinLevel() != null) {
            Specification<Player> levelGreaterThanOfEqualTo =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(root.get("level"), searchingDTO.getMinLevel());
            
            specification = specification.and(levelGreaterThanOfEqualTo);
        }
        
        if (searchingDTO.getMaxLevel() != null) {
            Specification<Player> levelLessThanOfEqualTo =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.lessThanOrEqualTo(root.get("level"), searchingDTO.getMaxLevel());
            
            specification = specification.and(levelLessThanOfEqualTo);
        }
        
        if (searchingDTO.getBanned() != null) {
            Specification<Player> bannedEqual =
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("banned"), searchingDTO.getBanned());
            
            specification = specification.and(bannedEqual);
        }
        
        return specification;
    }
}
