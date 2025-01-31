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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                Sort.by(request.getOrder().getFieldName())
        );
        
        return playerRepository.findAll(specification, pageRequest)
                .map(player -> mapper.map(player, PlayerDto.class))
                .toList();
    }
    
    public int getCount(FindPlayersRequest request) {
        Specification<Player> specification = new PlayerSpecification(request);
        return (int) playerRepository.count(specification);
    }
    
    public PlayerDto get(long id) {
        Optional<Player> player = playerRepository.findById(id);
        return player.isPresent() ? mapper.map(player, PlayerDto.class) : null;
    }
    
    @Transactional
    public PlayerDto create(PlayerDto request) {
        if (playerRepository.findByName(request.getName()) != null) {
            return null;
        }
        
        Player playerToCreate = mapper.map(request, Player.class);
        
        PlayerRace race = raceRepository.findByRace(request.getRace());
        PlayerProfession profession = professionRepository.findByProfession(request.getProfession());
        setPlayerLevelAndUntilNextLevel(playerToCreate, request.getExperience());
        
        playerToCreate.setRace(race);
        playerToCreate.setProfession(profession);
        Player createdPlayer = playerRepository.save(playerToCreate);
        return mapper.map(createdPlayer, PlayerDto.class);
    }
    
    @Transactional
    public PlayerDto update(PlayerDto request) {
        Player playerToUpdate = playerRepository.findById(request.getId()).orElse(null);
        if (playerToUpdate == null) {
            return null;
        }
        
        PlayerRace race = raceRepository.findByRace(request.getRace());
        PlayerProfession profession = professionRepository.findByProfession(request.getProfession());
        request.setRace(null);
        request.setProfession(null);
        mapper.map(request, playerToUpdate);
        playerToUpdate.setExperience(request.getExperience());
        setPlayerLevelAndUntilNextLevel(playerToUpdate, request.getExperience());
        
        Player updatedPlayer = playerRepository.save(playerToUpdate);
        playerRepository.setRaceAndProfession(race.getId(), profession.getId(), updatedPlayer.getId());
        return mapper.map(updatedPlayer, PlayerDto.class);
    }
    
    @Transactional
    public int delete(long id) {
        return playerRepository.removeById(id);
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
