package com.example.weekendproject.repository;

import com.example.weekendproject.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

  @Query(value = "SELECT id, date, user_id,substring(news, 1, 100) as news FROM POST ", nativeQuery = true)
  Page<Post> findAll(Pageable pageable);

  Optional<Post> findById(int id);
}
