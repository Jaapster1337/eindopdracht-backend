package com.example.eindopdrachtbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "usersZ")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "user")
    private List<Comment> listOfComments;
    @ManyToMany(mappedBy = "listOfFavorites")
    private List<Game> listOfFavorites;
}
