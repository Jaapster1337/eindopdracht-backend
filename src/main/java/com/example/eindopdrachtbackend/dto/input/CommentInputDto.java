package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentInputDto {

    private User user;
    private String content;
    private LocalDate postDate;
    private Long amountOfLikes;
    private Game game;
}
