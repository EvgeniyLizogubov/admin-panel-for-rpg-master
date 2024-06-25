package com.example.demo.repository;

import com.example.demo.entity.PlayerProfession;
import com.example.demo.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerProfessionRepository extends JpaRepository<PlayerProfession, Integer> {
    Optional<PlayerProfession> findByProfession(Profession profession);
}
