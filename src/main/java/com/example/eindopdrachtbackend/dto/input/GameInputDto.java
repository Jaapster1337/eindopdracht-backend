package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GameInputDto {
    private String name;
    private Long publisherId;
    private List<Long> genreId;
    private Long likes;
    private List<Comment> listOfComments;
    private List<User> listOfFavorites;
}
