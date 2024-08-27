package com.example.eindopdrachtbackend.dto.output;

import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserProfileDto {
    private String username;
    private String description;
    private List<CommentOutputDto> listOfComments;
    private List<GameOutputDto> listOfFavorites;
}
