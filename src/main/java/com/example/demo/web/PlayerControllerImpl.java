package com.example.demo.web;

import com.example.demo.dto.CreatePlayerRequest;
import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.dto.PlayerDto;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.dto.UpdatePlayerRequest;
import com.example.demo.service.PlayerService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class PlayerControllerImpl implements PlayerController {
    private final PlayerService service;
    private final ModelMapper mapper;
    
    @Override
    public List<PlayerResponse> getAll(FindPlayersRequest request) {
        log.info("Get all with params: {}", request);
        List<PlayerResponse> result = new ArrayList<>();
        List<PlayerDto> players = service.getAll(request);
        players.forEach(player -> result.add(mapper.map(player, PlayerResponse.class)));
        return result;
    }
    
    @Override
    public int getCount(FindPlayersRequest request) {
        log.info("Get count with params: {}", request);
        return service.getCount(request);
    }
    
    @Override
    public ResponseEntity<?> create(CreatePlayerRequest request) {
        log.info("Create with fields: {}", request);
        PlayerDto playerToCreate = mapper.map(request, PlayerDto.class);
        PlayerDto createdPlayer = service.create(playerToCreate);
        if (createdPlayer == null) {
            return new ResponseEntity<>("The name is already taken", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(mapper.map(createdPlayer, PlayerResponse.class), HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> get(long id) {
        log.info("Get with id = {}", id);
        PlayerDto player = service.get(id);
        if (player == null) {
            return new ResponseEntity<>("Entity with id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(player, PlayerResponse.class), HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> update(long id, UpdatePlayerRequest request) {
        log.info("Update with id = {} and fields: {}", id, request);
        PlayerDto playerToUpdate = mapper.map(request, PlayerDto.class);
        playerToUpdate.setId(id);
        PlayerDto updatedPlayer = service.update(playerToUpdate);
        if (updatedPlayer == null) {
            return new ResponseEntity<>("Entity with id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(updatedPlayer, PlayerResponse.class), HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> delete(long id) {
        log.info("Delete with id = {}", id);
        if (service.delete(id) == 0) {
            return new ResponseEntity<>("Entity with id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
