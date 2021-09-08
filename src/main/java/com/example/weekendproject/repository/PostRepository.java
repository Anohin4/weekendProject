package com.example.weekendproject.repository;

import com.example.weekendproject.model.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PostRepository extends CrudRepository<Post, Integer> {
    public List<Post> findAll();
    public Optional<Post> findById(int id);
}
