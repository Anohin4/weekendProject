package com.example.weekendproject.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Image {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private int id;

  private String link;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "POST_ID")
  private Post post;
}
