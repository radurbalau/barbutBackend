package com.example.barbut.barbutGame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/games")
public class BarbutGameController {

    private  final BarbutGameService barbutGameService;

    @Autowired
    public BarbutGameController(BarbutGameService barbutGameService) {
        this.barbutGameService = barbutGameService;
    }

    @GetMapping()
    public ResponseEntity<Object> getAllGamesThatAreOn(){
        return barbutGameService.gettAllGames();
    }
}
