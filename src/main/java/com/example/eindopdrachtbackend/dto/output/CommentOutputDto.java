package com.example.eindopdrachtbackend.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CommentOutputDto {
    private Long id;
    private Long userId;
    private String content;
    private LocalDate postDate;
    private Long amountOfLikes;
    private Long gameId;

}
