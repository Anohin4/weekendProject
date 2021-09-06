package com.example.weekendproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    boolean isActive = true;

    @ManyToMany
            @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_roles")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    Set<Role> roles;

}
