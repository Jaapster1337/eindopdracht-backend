package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.CommentInputDto;
import com.example.eindopdrachtbackend.dto.mapper.CommentMapper;
import com.example.eindopdrachtbackend.dto.output.CommentOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Comment;
import com.example.eindopdrachtbackend.repository.CommentRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentOutputDto> getAllComments(){
        Comment c = new Comment();
        List<Comment> allComments = commentRepository.findAll();
        List<CommentOutputDto> allCommentOutputlist = new ArrayList<>();
        for(Comment comment: allComments){
            allCommentOutputlist.add(CommentMapper.fromModelToOutputDto(comment));
        }
        return  allCommentOutputlist;
    }


    public CommentOutputDto createComment(CommentInputDto commentInputDto) {
        Comment c = commentRepository.save(CommentMapper.fromInputDtoToModel(commentInputDto));
        return CommentMapper.fromModelToOutputDto(c);
    }

    public CommentOutputDto getCommentById(long id){
        Optional<Comment> c =commentRepository.findById(id);
        if(c.isPresent()){
            return CommentMapper.fromModelToOutputDto(c.get());
        } else {
            throw new RecordNotFoundException("No comment with this id was found");
        }
    }

    public CommentOutputDto updateComment(long id, CommentInputDto commentInputDto){
        Optional<Comment> c = commentRepository.findById(id);
        if(c.isPresent()){
            c.get().setUser(commentInputDto.getUser());
            c.get().setContent(commentInputDto.getContent());
            c.get().setPostDate(commentInputDto.getPostDate());
            c.get().setAmountOfLikes(commentInputDto.getAmountOfLikes());
            c.get().setGame(commentInputDto.getGame());
            commentRepository.save(c.get());
            return CommentMapper.fromModelToOutputDto(c.get());
        } else {
            throw new RecordNotFoundException("no comments with "+id+" found");
        }
    }

    public String deleteComment(long id){
        Optional<Comment> c = commentRepository.findById(id);
        if(c.isPresent()){
            commentRepository.delete(c.get());
            return "Comment with id "+id+" has been removed";
        } else {
            throw new RecordNotFoundException("no comments with "+id+" found");
        }
    }
}
