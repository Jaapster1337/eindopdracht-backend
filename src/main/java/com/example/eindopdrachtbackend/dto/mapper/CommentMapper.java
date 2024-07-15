package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.CommentInputDto;
import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.CommentOutputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.Game;

public class CommentMapper {

    public static Comment fromInputDtoToModel(CommentInputDto commentInputDto){
        Comment c = new Comment();
        c.setUser(commentInputDto.getUser());
        c.setContent(commentInputDto.getContent());
        c.setPostDate(commentInputDto.getPostDate());
        c.setAmountOfLikes(commentInputDto.getAmountOfLikes());
        c.setGame(commentInputDto.getGame());
        return c;
    }

    public static CommentOutputDto fromModelToOutputDto(Comment comment){
        CommentOutputDto commentOutputDto = new CommentOutputDto();
        commentOutputDto.setId(comment.getId());
        commentOutputDto.setUser(comment.getUser());
        commentOutputDto.setContent(comment.getContent());
        commentOutputDto.setPostDate(comment.getPostDate());
        commentOutputDto.setAmountOfLikes(comment.getAmountOfLikes());
        commentOutputDto.setGame(comment.getGame());
        return commentOutputDto;
    }
}
