package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GameInputDto {
    @NotEmpty
    private String name;
    @NotBlank
    private Long publisherId;
    private List<Long> genreId;
    @PositiveOrZero
    private Long likes;
    private List<Comment> listOfComments;
    private List<User> listOfFavorites;
}
