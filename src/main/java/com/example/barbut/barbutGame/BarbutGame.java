package com.example.barbut.barbutGame;


import com.example.barbut.player.Player;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "barbut_game")
@Getter
@Setter
public class BarbutGame {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;
    @Column(name = "diceroll")
    private int diceroll;
    @Column(name = "is_on")
    private boolean isOn;

    @JsonBackReference
    @JoinColumn(name = "player_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    public BarbutGame(String name, int diceroll) {
        this.name = name;
        this.diceroll = diceroll;
    }


    public BarbutGame() {

    }
}
