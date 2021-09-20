package com.example.weekendproject.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @Column(name = "DATE")
  private String date;

  @Column(name = "NEWS", length = 10000)
  @NotEmpty
  private String news;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<Image> image;

}
