package com.example.weekendproject.service;

import com.example.weekendproject.model.Image;
import com.example.weekendproject.model.Post;
import com.example.weekendproject.repository.ImageRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageService {
  final ImageRepository imageRepository;

  @Value(value = "${upload.pathForImages}")
  String source;

  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  @PostConstruct
  public void init() {
    //creating upload dir
    Path root = Paths.get(source);
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Image saveImage(MultipartFile file, Post post){
    Path root = Paths.get(source);
    Image image = new Image();
    String name = RandomStringUtils.randomAlphanumeric(15) +".jpg";
    try{
      Files.copy(file.getInputStream(), root.resolve(name));
      image.setLink(root.toString() + "\\" +  name);
      image.setPost(post);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return imageRepository.save(image);
  }

  public List<Image> findAllByPost(Post post) {
    return imageRepository.findAllByPost(post);
  }

}
