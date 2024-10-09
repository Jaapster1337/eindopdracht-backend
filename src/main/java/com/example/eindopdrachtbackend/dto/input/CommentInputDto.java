package com.example.eindopdrachtbackend.dto.input;

import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentInputDto {

    private String userId;
    @NotBlank
    private String content;
    private LocalDate postDate;
    @PositiveOrZero
    private Long amountOfLikes;
    private Long gameId;

}
