package com.example.barbut.barbutGame;

import com.example.barbut.barbutGame.BarbutGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbutRepository extends JpaRepository<BarbutGame,Integer> {
    void deleteBarbutGameByPlayer_Id(Integer playerId);
}
