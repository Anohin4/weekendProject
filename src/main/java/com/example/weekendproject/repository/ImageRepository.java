package com.example.weekendproject.repository;

import com.example.weekendproject.model.Image;
import com.example.weekendproject.model.Post;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ImageRepository extends CrudRepository<Image, Integer> {
  List<Image> findAllByPost(Post post);

}
