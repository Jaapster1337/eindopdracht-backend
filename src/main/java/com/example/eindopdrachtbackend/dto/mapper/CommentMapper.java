package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.CommentInputDto;
import com.example.eindopdrachtbackend.dto.output.CommentOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.User;
import com.example.eindopdrachtbackend.repository.GameRepository;
import com.example.eindopdrachtbackend.repository.UserRepository;

import java.util.Optional;

public class CommentMapper {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public CommentMapper(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    public static Comment fromInputDtoToModel(CommentInputDto commentInputDto, UserRepository userRepository, GameRepository gameRepository){
        Comment c = new Comment();
        Optional<User> user = userRepository.findById(commentInputDto.getUserId());
        if(user.isPresent()) {
            c.setUser(user.get());
        } else {
            throw new RecordNotFoundException("No User with this id was found");
        }

        c.setContent(commentInputDto.getContent());
        c.setPostDate(commentInputDto.getPostDate());
        c.setAmountOfLikes(commentInputDto.getAmountOfLikes());
        Optional<Game> game = gameRepository.findById(commentInputDto.getGameId());
        if (game.isPresent()){
            c.setGame(game.get());
        } else {
            throw new RecordNotFoundException("No Game with this id was found");
        }

        return c;
    }

    public static CommentOutputDto fromModelToOutputDto(Comment comment){
        CommentOutputDto commentOutputDto = new CommentOutputDto();
        commentOutputDto.setId(comment.getId());
        commentOutputDto.setUserId(comment.getUser().getId());
        commentOutputDto.setContent(comment.getContent());
        commentOutputDto.setPostDate(comment.getPostDate());
        commentOutputDto.setAmountOfLikes(comment.getAmountOfLikes());
        commentOutputDto.setGameId(comment.getGame().getId());
        return commentOutputDto;
    }
}
