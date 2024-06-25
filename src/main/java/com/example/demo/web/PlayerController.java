package com.example.demo.web;

import com.example.demo.dto.*;
import com.example.demo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class PlayerController {
    private final PlayerService service;
    private final ModelMapper mapper;
    
    @GetMapping
    public List<PlayerResponse> getAll(@Valid FindPlayersRequest request) {
        log.info("Get all with params: {}", request);
        List<PlayerResponse> result = new ArrayList<>();
        List<PlayerDTO> players = service.getAll(request);
        players.forEach(player -> result.add(mapper.map(player, PlayerResponse.class)));
        return result;
    }
    
    @GetMapping("/count")
    public int getCount(@Valid FindPlayersRequest request) {
        log.info("Get count with params: {}", request);
        return service.getCount(request);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerResponse> create(@Valid @RequestBody CreatePlayerRequest request) {
        log.info("Create with fields: {}", request);
        PlayerDTO playerToCreate = mapper.map(request, PlayerDTO.class);
        PlayerDTO createdPlayer = service.create(playerToCreate);
        return new ResponseEntity<>(mapper.map(createdPlayer, PlayerResponse.class), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable @Positive long id) {
        log.info("Get with id = {}", id);
        PlayerDTO player = service.get(id);
        if (player == null) {
            return new ResponseEntity<>("Entity with id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(player, PlayerResponse.class), HttpStatus.OK);
    }
    
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable @Positive long id, @Valid @RequestBody UpdatePlayerRequest request) {
        log.info("Update with id = {} and fields: {}", id, request);
        PlayerDTO playerToUpdate = mapper.map(request, PlayerDTO.class);
        playerToUpdate.setId(id);
        PlayerDTO updatedPlayer = service.update(playerToUpdate);
        if (updatedPlayer == null) {
            return new ResponseEntity<>("Entity with id=" + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mapper.map(updatedPlayer, PlayerResponse.class), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive long id) {
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
