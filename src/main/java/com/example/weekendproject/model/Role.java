package com.example.weekendproject.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
public class Role {
    @Column(name = "ROLE_ID")
    @Id
    int id;
    @Column(name = "ROLE")
    String role;

}
