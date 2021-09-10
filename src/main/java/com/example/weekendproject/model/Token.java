package com.example.weekendproject.model;

import com.example.weekendproject.model.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Token {
    @Id
    String UUID;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    User user;


    LocalDateTime dateOfExpire;

    public Token() {
        this.UUID = java.util.UUID.randomUUID().toString();
        this.dateOfExpire = LocalDateTime.now().plusDays(1);
    }

}
