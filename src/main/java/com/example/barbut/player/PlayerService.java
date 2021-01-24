package com.example.barbut.player;


import com.example.barbut.barbutGame.BarbutGame;
import com.example.barbut.barbutGame.BarbutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PlayerService {

    public PlayerRepository playerRepository;
    public BarbutRepository barbutRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository,BarbutRepository barbutRepository){
        this.barbutRepository = barbutRepository;
        this.playerRepository = playerRepository;
    }

    public List<Player> getUsers(){
            return playerRepository.findAll();
    }

    public ResponseEntity<Object> addNewUser(Player user){
        Optional<Player> userOptional = playerRepository.findUserByUsername(user.getUsername());
        if(userOptional.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.ALREADY_REPORTED);
        }

        playerRepository.save(new Player(user.getUsername(),user.getPassword()));

        return new ResponseEntity<>("user saved" + user.getUsername(),HttpStatus.OK);
    }

    public ResponseEntity<Object> getExistingUser(Player user){
        Optional<Player> userOptional = playerRepository.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("username not signed id",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.getUsername() +" logged in",HttpStatus.OK);
    }

    public int addABarbutGame(Integer userThatSubmittedGameId,Integer otherPlayerId){
        int myDiceroll = getRandomNumberInRange(1,6) + getRandomNumberInRange(1,6);
        int ret = -1;
        BarbutGame test=null;

        Optional<Player> presentUser = playerRepository.findById(userThatSubmittedGameId);
        Optional<Player> userChallenged = playerRepository.findById(otherPlayerId);

        if(presentUser.isEmpty() || userChallenged.isEmpty())
            return 0;

        System.out.println(presentUser.get());
        System.out.println(userChallenged.get());

        //verific in lista mea daca e cineva cu numele lui
        for(BarbutGame barbutGame:presentUser.get().getBarbutGameList()){
            if(barbutGame.getName().equals(userChallenged.get().getUsername())){
                int otherPlayerDices = barbutGame.getDiceroll();

                if(barbutGame.isOn()) {
                    if (myDiceroll >= otherPlayerDices) {
                        barbutGame.setOn(false);
                        ret =  1;
                        test = barbutGame;
                        break;
                    } else {
                        barbutGame.setOn(false);
                        ret = 2;
                        test= barbutGame;
                        break;
                    }
                }
            }
        }

        if(ret == 1 || ret == 2){
            //barbutRepository.deleteById(test.getId());
            //barbutRepository.deleteBarbutGameByPlayer_Id(otherPlayerId);
            return ret;
        }

        //eu fac requestul primul
        BarbutGame barbutGame = new BarbutGame(presentUser.get().getUsername(),myDiceroll);
//        userChallenged.get().getBarbutGameList().add(barbutGame);
        List<BarbutGame> list = userChallenged.get().getBarbutGameList();
        barbutGame.setOn(true);
        list.add(barbutGame);
        userChallenged.get().setBarbutGameList(list);
        barbutGame.setPlayer(userChallenged.get());
        barbutRepository.save(barbutGame);

        return 3;

//            //verifica daca am in hashmapul meu cheia cu numele lui(el mi-a dat request primul)
//            if(presentUser.get().getBarbutGames().containsKey(userChallenged.get().getUsername())){
//                Integer otherPlayerDices = presentUser.get().getBarbutGames().get(userChallenged.get().getUsername());
//                if(otherPlayerDices >= myDiceroll){
//                    //other player won
//
//                    //sterg intrarea lui la mine
//                    presentUser.get().getBarbutGames().remove(userChallenged.get().getUsername());
//                    return 1;
//                }else
//                {
//                    //present player won(i won)
//
//                    //sterg intrarea lui de la mine
//                    presentUser.get().getBarbutGames().remove(userChallenged.get().getUsername());
//                    return 2;
//                }
//            }else {
//                //inseamna ca eu sunt primul care face requestul de barbut, si pun in
//                //hash mapul lui diceroll ul meu
//                Optional<Player> player = playerRepository.findById(userThatSubmittedGameId);
//                player.get().getBarbutGames().put(userChallenged.get().getUsername(),myDiceroll);
//                //i submitted a request
//                return 3;
//            }



        //error return
        //return 0;
    }





    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
