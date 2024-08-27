package com.example.eindopdrachtbackend.dto.output;

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
public class UserOutputDto {

    private String username;
    private String password;
    private String email;
    private String description;
    private List<CommentOutputDto> listOfComments;
    private List<GameOutputDto> listOfFavorites;
    @JsonSerialize
    private Set<Authority> authorities;
}
