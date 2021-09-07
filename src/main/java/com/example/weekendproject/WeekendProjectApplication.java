package com.example.weekendproject;

import com.example.weekendproject.model.Role;
import com.example.weekendproject.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WeekendProjectApplication {
    final
    RoleRepository roleRepository;

    public WeekendProjectApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WeekendProjectApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void rolesOnStart() throws Exception{
        roleRepository.save(new Role(1, "ROLE_ADMIN"));
        roleRepository.save(new Role(2, "ROLE_USER"));
    }
}
