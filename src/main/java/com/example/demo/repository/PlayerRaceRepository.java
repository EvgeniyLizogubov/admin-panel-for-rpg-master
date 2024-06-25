package com.example.demo.repository;

import com.example.demo.entity.PlayerRace;
import com.example.demo.entity.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRaceRepository extends JpaRepository<PlayerRace, Integer> {
    Optional<PlayerRace> findByRace(Race race);
}
