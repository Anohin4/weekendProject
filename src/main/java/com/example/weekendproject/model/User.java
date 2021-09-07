package com.example.weekendproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    int id;

    @NotEmpty(message = "Username can't be empty")
    @Column(name = "USER_NAME")
    String username;

    @Length(min=6, message = "Password must be at least 6 chars")
    @Column(name = "PASSWORD")
    private String password;

    @Email(message = "wrong email format")
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name="IS_ACTIVE")
    private boolean isActive = true;
    @ManyToMany
            @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    Set<Role> roles;
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

}
