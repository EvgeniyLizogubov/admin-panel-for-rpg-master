package com.example.demo.web;

import com.example.demo.dto.RequestCreatingDTO;
import com.example.demo.dto.RequestSearchingDTO;
import com.example.demo.dto.RequestUpdatingDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.entity.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class PlayerController {
    @GetMapping
    public List<Player> getAll(@Valid RequestSearchingDTO requestDTO) {
        log.info("Get all with params: {}", requestDTO);
        return null;
    }
    
    @GetMapping("/count")
    public Integer getCount(@Valid RequestSearchingDTO requestDTO) {
        log.info("Get count with params: {}", requestDTO);
        return null;
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO create(@Valid @RequestBody RequestCreatingDTO requestDTO) {
        log.info("Create with fields: {}", requestDTO);
        return null;
    }
    
    @GetMapping("/{id}")
    public ResponseDTO get(@PathVariable @Positive int id) {
        log.info("Get with id = {}", id);
        return null;
    }
    
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO update(@PathVariable @Positive int id, @Valid @RequestBody RequestUpdatingDTO requestDTO) {
        log.info("Update with id = {} and fields: {}", id, requestDTO);
        return null;
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive int id) {
        log.info("Delete with id = {}", id);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
