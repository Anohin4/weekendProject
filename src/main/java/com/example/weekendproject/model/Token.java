package com.example.weekendproject.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Token {
    @Id
    String id;

    @OneToOne(mappedBy = "token")
    User user;


    LocalDateTime dateOfExpire;

    public Token() {
        this.id = java.util.UUID.randomUUID().toString();
        this.dateOfExpire = LocalDateTime.now().plusDays(1);
    }

}
