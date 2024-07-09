package com.example.demo.web;

import com.example.demo.dto.CreatePlayerRequest;
import com.example.demo.dto.FindPlayersRequest;
import com.example.demo.dto.PlayerResponse;
import com.example.demo.dto.UpdatePlayerRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Validated
public interface PlayerOperations {
    @GetMapping
    List<PlayerResponse> getAll(@Valid FindPlayersRequest request);
    
    @GetMapping("/count")
    int getCount(@Valid FindPlayersRequest request);
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> create(@Valid @RequestBody CreatePlayerRequest request);
    
    @GetMapping("/{id}")
    ResponseEntity<?> get(@PathVariable @Positive long id);
    
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> update(@PathVariable @Positive long id, @Valid @RequestBody UpdatePlayerRequest request);
    
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable @Positive long id);
}
