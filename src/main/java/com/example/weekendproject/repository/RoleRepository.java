package com.example.weekendproject.repository;

import com.example.weekendproject.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface RoleRepository extends CrudRepository<Role, Integer> {
    public Set<Role> findAllByRole(String role);
    public Role findByRole(String role);
}
