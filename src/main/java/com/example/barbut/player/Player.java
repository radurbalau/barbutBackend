package com.example.barbut.player;


import com.example.barbut.barbutGame.BarbutGame;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Player {
    @Id
    @SequenceGenerator(name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    //private Integer ballance;

    @JsonManagedReference
    @OneToMany(mappedBy = "player",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BarbutGame> barbutGameList;


    public Player(){}

    public Player(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
