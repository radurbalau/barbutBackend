package com.example.barbut.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerRepository playerRepository) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
    }

    @CrossOrigin
    @GetMapping
    public List<Player> allUsers(){
        return playerService.getUsers();
    }

    @CrossOrigin
    @GetMapping("/getbyName/{playerName}")
    public ResponseEntity<Object> getPlayerByName(@PathVariable String playerName){
        Optional<Player> player = playerRepository.findUserByUsername(playerName);

        return new ResponseEntity<>(player,HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/register")
    public ResponseEntity<Object> registerUser(@RequestBody Player user){
       return playerService.addNewUser(user);
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody Player user){
        return playerService.getExistingUser(user);
    }

    @CrossOrigin
    @GetMapping(value = "/barbut")
    public ResponseEntity<Object> postABarbutGame(
            @RequestParam(required = false,name = "myId") Integer myId,
            @RequestParam(required = false,name = "otherId") Integer otherId){
        int result = playerService.addABarbutGame(myId,otherId);

        if(result == 1){
            return new ResponseEntity<>("Player " +
                    playerRepository.findById(otherId).get().getUsername() + " has Won " ,HttpStatus.OK);

        }else if(result == 2){
            return new ResponseEntity<>("You won !",HttpStatus.OK);
        }else if(result == 3)
            return new ResponseEntity<>("You submitted a barbut request ! ",HttpStatus.OK);

        //errorcase
        return new ResponseEntity<>("ERROR, players are non existent",HttpStatus.NOT_FOUND);
    }

}
