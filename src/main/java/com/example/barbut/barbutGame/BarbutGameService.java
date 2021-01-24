package com.example.barbut.barbutGame;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BarbutGameService {

    private final BarbutRepository barbutRepository ;

    @Autowired
    public BarbutGameService(BarbutRepository barbutRepository) {
        this.barbutRepository = barbutRepository;
    }

    public ResponseEntity<Object> gettAllGames(){
        return new ResponseEntity<>(barbutRepository.findAll().stream().filter(BarbutGame::isOn), HttpStatus.OK);
    }
}
