package com.example.eindopdrachtbackend.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter

public class GameOutputDto {
    private Long id;
    private String name;
    private Long publisherId;
    private List<Long> genreId;
    private Long likes;
    private List<CommentOutputDto> listOfComments;
    private int amountOfFavorites;
    private ImageOutputDto gameCover;
}
