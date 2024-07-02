package com.example.demo.repository;

import com.example.demo.entity.PlayerProfession;
import com.example.demo.entity.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerProfessionRepository extends JpaRepository<PlayerProfession, Integer> {
    PlayerProfession findByProfession(Profession profession);
}
