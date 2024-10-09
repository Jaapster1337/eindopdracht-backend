package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Authority;
import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.Game;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class UserInputDto {
    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;
    @NotBlank
    @Column(nullable = false, length = 255)
    @Size(min = 8)
    private String password;
    @Email(message = "Please enter a valid email")
    private String email;
    private String description;
    private List<Comment> listOfComments;
    private List<Game> listOfFavorites;
    @JsonSerialize
    private Set<Authority> authorities;
}
