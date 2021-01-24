package com.example.barbut.player;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Integer> {
    Optional<Player> findUserByUsernameAndPassword(String username, String password);
    Optional<Player> findUserByUsername(String username);

}
