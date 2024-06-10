package com.example.demo.web;

import com.example.demo.dto.RequestCreatingDTO;
import com.example.demo.dto.RequestSearchingDTO;
import com.example.demo.dto.RequestUpdatingDTO;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.error.NotFoundException;
import com.example.demo.service.PlayerService;
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
    private final PlayerService service;
    
    @GetMapping
    public List<ResponseDTO> getAll(@Valid RequestSearchingDTO requestDTO) {
        log.info("Get all with params: {}", requestDTO);
        return service.getAll(requestDTO);
    }
    
    @GetMapping("/count")
    public int getCount(@Valid RequestSearchingDTO requestDTO) {
        log.info("Get count with params: {}", requestDTO);
        return service.getCount(requestDTO);
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody RequestCreatingDTO requestDTO) {
        log.info("Create with fields: {}", requestDTO);
        ResponseDTO responseDTO = service.create(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseDTO get(@PathVariable @Positive long id) {
        log.info("Get with id = {}", id);
        return service.get(id);
    }
    
    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO update(@PathVariable @Positive long id, @Valid @RequestBody RequestUpdatingDTO requestDTO) {
        log.info("Update with id = {} and fields: {}", id, requestDTO);
        return service.update(id, requestDTO);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive long id) {
        log.info("Delete with id = {}", id);
        service.delete(id);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Validation error " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
