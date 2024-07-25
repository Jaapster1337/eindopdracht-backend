package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Authority;
import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.Game;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class UserInputDto {
    private String username;
    private String password;
    private String email;
    private String description;
    private List<Comment> listOfComments;
    private List<Game> listOfFavorites;
    @JsonSerialize
    private Set<Authority> authorities;
}
