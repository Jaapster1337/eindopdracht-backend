package com.example.eindopdrachtbackend.controller;

import com.example.eindopdrachtbackend.dto.input.UserInputDto;
import com.example.eindopdrachtbackend.dto.output.UserOutputDto;
import com.example.eindopdrachtbackend.dto.output.UserProfileDto;
import com.example.eindopdrachtbackend.dto.output.UserSelfProfileDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private  final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserOutputDto> createUser(@Valid @RequestBody UserInputDto userInputDto){
        UserOutputDto userOutputDto = userService.createUser(userInputDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userOutputDto.getUsername()).toUri();
        return ResponseEntity.created(uri).body(userOutputDto);
    }

    @GetMapping
    public ResponseEntity<List<UserOutputDto>> returnAllUsers(){
        return  ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDto> getUserByUsername(@Valid @PathVariable String username) throws RecordNotFoundException{
        return ResponseEntity.ok().body(userService.getUserByUsername(username, true));
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserSelfProfileDto> updateUser(@Valid @PathVariable String username, @RequestBody UserInputDto user){
        return ResponseEntity.ok().body(userService.updateTv(username, user));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@Valid @PathVariable String username){
        return ResponseEntity.ok().body(userService.deleteUser(username));
    }
}
