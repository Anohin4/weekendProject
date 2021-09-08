package com.example.weekendproject;

import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.RoleRepository;
import com.example.weekendproject.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Set;

@SpringBootApplication
public class WeekendProjectApplication {
    final
    RoleRepository roleRepository;

    final UserRepository userRepository;

    public WeekendProjectApplication(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WeekendProjectApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void rolesOnStart() throws Exception{
        Role role = new Role(1, "ROLE_ADMIN");
        Role role2 = new Role(2, "ROLE_USER");
        roleRepository.save(role);
        roleRepository.save(role2);
        Set<Role> roles = Set.of(role,role2);
        User user = new User();
        user.setPassword("adminadmin");
        user.setRoles(roles);
        user.setUsername("admin");
        user.setEmail("admin@admin");
        userRepository.save(user);
    }
}
