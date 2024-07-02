package com.example.demo.service;

import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.dto.PlayerDto;
import com.example.demo.entity.Player;
import com.example.demo.entity.PlayerProfession;
import com.example.demo.entity.PlayerRace;
import com.example.demo.repository.PlayerProfessionRepository;
import com.example.demo.repository.PlayerRaceRepository;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.util.PlayerSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerRaceRepository raceRepository;
    private final PlayerProfessionRepository professionRepository;
    private final ModelMapper mapper;
    
    public List<PlayerDto> getAll(FindPlayersRequest request) {
        Specification<Player> specification = new PlayerSpecification(request);
        
        PageRequest pageRequest = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.ASC,
                request.getOrder().getFieldName()
        );
        
        Page<Player> players = playerRepository.findAll(specification, pageRequest);
        List<PlayerDto> result = new ArrayList<>();
        players.forEach(player -> result.add(mapper.map(player, PlayerDto.class)));
        return result;
    }
    
    public int getCount(FindPlayersRequest request) {
        Specification<Player> specification = (root, query, criteriaBuilder) ->
                new PlayerSpecification(request).toPredicate(root, query, criteriaBuilder);
        return (int) playerRepository.count(specification);
    }
    
    public PlayerDto get(long id) {
        Optional<Player> player = playerRepository.findById(id);
        return player.isPresent() ? mapper.map(player, PlayerDto.class) : null;
    }
    
    public PlayerDto create(PlayerDto request) {
        Player playerToCreate = mapper.map(request, Player.class);
        
        PlayerRace race = raceRepository.findByRace(request.getRace());
        PlayerProfession profession = professionRepository.findByProfession(request.getProfession());
        setPlayerLevelAndUntilNextLevel(playerToCreate, request.getExperience());
        
        playerToCreate.setRace(race);
        playerToCreate.setProfession(profession);
        Player createdPlayer = playerRepository.save(playerToCreate);
        return mapper.map(createdPlayer, PlayerDto.class);
    }
    
    public PlayerDto update(PlayerDto request) {
        Player playerToUpdate = playerRepository.findById(request.getId()).orElse(null);
        if (playerToUpdate == null) {
            return null;
        }
        
        mapper.map(request, playerToUpdate);
        setPlayerLevelAndUntilNextLevel(playerToUpdate, request.getExperience());
        Player updatedPlayer = playerRepository.save(playerToUpdate);
        return mapper.map(updatedPlayer, PlayerDto.class);
    }
    
    public int delete(long id) {
        return playerRepository.delete(id);
    }
    
    private void setPlayerLevelAndUntilNextLevel(Player player, Integer experience) {
        if (experience != null) {
            int level = (int) ((Math.sqrt(2500 + 200 * experience) - 50) / 100);
            int untilNextLevel = 50 * (level + 1) * (level + 2) - experience;
            player.setLevel(level);
            player.setUntilNextLevel(untilNextLevel);
        }
    }
}
