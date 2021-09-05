package com.example.weekendproject.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @NotEmpty
    String userName;

    @NotEmpty
    @Length(min = 6)
    String password;

    String email;
    String firstName;
    String lastName;
    boolean isActive;

    @OneToMany(mappedBy = "roleName")
    List<Roles> roles;
}
