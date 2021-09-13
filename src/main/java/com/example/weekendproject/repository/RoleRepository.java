package com.example.weekendproject.repository;

import com.example.weekendproject.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface RoleRepository extends CrudRepository<Role, Integer> {

  Set<Role> findAllByRole(String role);

  Role findByRole(String role);
}
