package com.example.eindopdrachtbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;
    @NotBlank
    @Column(nullable = false, length = 255)
    @Size(min = 8)
    private String password;
    @Email(message = "Please enter a valid email")
    private String email;
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
    private String description;
    @OneToMany(mappedBy = "user")
    private List<Comment> listOfComments;
    @ManyToMany(mappedBy = "listOfFavorites")
    private List<Game> listOfFavorites;
}
