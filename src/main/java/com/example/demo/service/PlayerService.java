package com.example.demo.service;

import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.dto.PlayerDTO;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.util.PlayerSpecificationBuilder;
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
    private final PlayerRepository repository;
    private final ModelMapper mapper;
    
    public List<PlayerResponse> getAll(FindPlayersRequest request) {
        Specification<Player> specification = new PlayerSpecificationBuilder(request).getSpecification();
        PageRequest pageRequest = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.ASC,
                request.getOrder().getFieldName()
        );
        
        Page<Player> players = repository.findAll(specification, pageRequest);
        List<PlayerResponse> result = new ArrayList<>();
        players.forEach(player -> result.add(mapper.map(player, PlayerResponse.class)));
        return result;
    }
    
    public int getCount(FindPlayersRequest request) {
        Specification<Player> specification = new PlayerSpecificationBuilder(request).getSpecification();
        return (int) repository.count(specification);
    }
    
    public PlayerResponse get(long id) {
        Optional<Player> player = repository.findById(id);
        return player.isPresent() ? mapper.map(player, PlayerResponse.class) : null;
    }
    
    public PlayerResponse create(PlayerDTO request) {
        Player playerToCreate = mapper.map(request, Player.class);
        setPlayerLevelAndUntilNextLevel(playerToCreate, request.getExperience());
        Player createdPlayer = repository.save(playerToCreate);
        return mapper.map(createdPlayer, PlayerResponse.class);
    }
    
    public PlayerResponse update(PlayerDTO request) {
        Player playerToUpdate = repository.findById(request.getId()).orElse(null);
        if (playerToUpdate != null) {
            mapper.map(request, playerToUpdate);
            setPlayerLevelAndUntilNextLevel(playerToUpdate, request.getExperience());
            return mapper.map(repository.save(playerToUpdate), PlayerResponse.class);
        } else {
            return null;
        }
    }
    
    public int delete(long id) {
        return repository.delete(id);
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
