package com.example.weekendproject.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
public class Role {
    @Id
    int id;
    String role;

    @ManyToMany(mappedBy = "roles")
    Set<User> user;
}
