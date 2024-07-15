package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.CommentInputDto;
import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.output.CommentOutputDto;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentOutputDto> createComment(@Valid @RequestBody CommentInputDto commentInputDto){
        CommentOutputDto commentOutputDto = commentService.createComment(commentInputDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(commentOutputDto.getId()).toUri();
        return ResponseEntity.created(uri).body(commentOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<CommentOutputDto>> returnAllComments(){
        return ResponseEntity.ok().body(commentService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentOutputDto> getCommentById(@PathVariable int id) throws RecordNotFoundException{
        return ResponseEntity.ok().body(commentService.getCommentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentOutputDto> updateComment(@PathVariable int id, @RequestBody CommentInputDto comment){
        return ResponseEntity.ok().body(commentService.updateComment(id, comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id){
        return ResponseEntity.ok().body(commentService.deleteComment(id));
    }
}
