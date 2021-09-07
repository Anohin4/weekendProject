package com.example.weekendproject.model;

import javax.persistence.*;

@Entity
public class Post {
    @Column(name = "POST ID")
    @Id
    private int id;

    @Column(name = "AUTHOR")
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "DATE")
    private String date;

    @Column(name = "POST")
    private String post;
}
