package com.example.weekendproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Roles {
    @Id
    int id;


    String roleName;
}
