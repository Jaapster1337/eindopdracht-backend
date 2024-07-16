package com.example.eindopdrachtbackend.dto.output;

import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CommentOutputDto {
    private long id;
    private User user;
    private String content;
    private LocalDate postDate;
    private Long amountOfLikes;
    private Game game;
}
