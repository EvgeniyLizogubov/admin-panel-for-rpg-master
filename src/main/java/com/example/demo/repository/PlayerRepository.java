package com.example.demo.repository;

import com.example.demo.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {
    int removeById(Long id);
    
    Player findByName(String name);
    
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE player SET race_id=?1, profession_id=?2 WHERE id=?3")
    void setRaceAndProfession(int raceId, int professionId, Long id);
}
