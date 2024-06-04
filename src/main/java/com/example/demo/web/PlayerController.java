package com.example.demo.web;

import com.example.demo.entity.Player;
import com.example.demo.entity.Profession;
import com.example.demo.entity.Race;
import com.example.demo.filter.PlayerOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/players", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class PlayerController {
    PlayerService service;
    
    @GetMapping
    public List<Player> getAll(@RequestParam String name,
                               @RequestParam String title,
                               @RequestParam Race race,
                               @RequestParam Profession profession,
                               @RequestParam Long after,
                               @RequestParam Long before,
                               @RequestParam Boolean banned,
                               @RequestParam Integer minExperience,
                               @RequestParam Integer maxExperience,
                               @RequestParam Integer minLevel,
                               @RequestParam Integer maxLevel,
                               @RequestParam PlayerOrder playerOrder,
                               @RequestParam Integer pageNumber,
                               @RequestParam Integer pageSize) {
        
    }
}
