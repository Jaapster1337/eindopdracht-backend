package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.UserInputDto;
import com.example.eindopdrachtbackend.dto.mapper.UserMapper;
import com.example.eindopdrachtbackend.dto.output.UserOutputDto;
import com.example.eindopdrachtbackend.dto.output.UserProfileDto;
import com.example.eindopdrachtbackend.dto.output.UserSelfProfileDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.Game;
import com.example.eindopdrachtbackend.model.User;
import com.example.eindopdrachtbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserOutputDto> getAllUser() {
        User u = new User();
        List<User> allUsers = userRepository.findAll();
        List<UserOutputDto> allUserDtoList = new ArrayList<>();
        for (User user : allUsers) {
            allUserDtoList.add(UserMapper.fromModelToOutputDto(user));
        }
        return allUserDtoList;
    }

    public UserOutputDto createUser(UserInputDto userInputDto) {
        User u = userRepository.save(UserMapper.fromInputDtoToModel(userInputDto));
        return UserMapper.fromModelToOutputDto(u);
    }

    public UserOutputDto getUserForSignIn(String username){
        Optional<User> u = userRepository.findUserByUsername(username);
        if (u.isEmpty()){
            throw new RecordNotFoundException("No user with " + username + " as username was found." );
        } else {
            return UserMapper.fromModelToOutputDto(u.get());
        }
    }

    public UserProfileDto getUserByUsername(String username, boolean auth) {
        Optional<User> u = userRepository.findUserByUsername(username);
        if (u.isPresent()) {
            if (auth) {
                return UserMapper.authenticatedFromUserToSelfProfile(u.get());
            } else {
                return UserMapper.fromUserToUserProfile(u.get());
            }
        } else {
            throw new RecordNotFoundException("No user with " + username + " as username was found");
        }
    }

    public UserSelfProfileDto updateTv(String username, UserInputDto userInputDto){
        Optional<User> u = userRepository.findUserByUsername(username);
        if(u.isPresent()){
            User user = u.get();
            user.setUsername(userInputDto.getUsername());
            user.setDescription(userInputDto.getDescription());
            user.setEmail(userInputDto.getEmail());
            user.setListOfComments(userInputDto.getListOfComments());
            user.setListOfFavorites(userInputDto.getListOfFavorites());
            userRepository.save(u.get());
            return UserMapper.authenticatedFromUserToSelfProfile(u.get());
        }
        throw new  RecordNotFoundException("No user with username "+username+" was found");
    }

    public String deleteUser(String username){
        Optional<User> u = userRepository.findUserByUsername(username);

        if(u.isPresent()){
            User user = u.get();
            List<Game> games = user.getListOfFavorites();
            for (Game game : games){
                game.getListOfFavorites().remove(user);
            }
            userRepository.delete(user);
            return "User with "+ username+" has been removed";
        } else {
            throw new RecordNotFoundException("No user was found");
        }
    }

}
