package com.example.eindopdrachtbackend.dto.mapper;

import com.example.eindopdrachtbackend.dto.input.UserInputDto;
import com.example.eindopdrachtbackend.dto.output.UserOutputDto;
import com.example.eindopdrachtbackend.dto.output.UserProfileDto;
import com.example.eindopdrachtbackend.dto.output.UserSelfProfileDto;
import com.example.eindopdrachtbackend.model.User;

public class UserMapper {
    public static User fromInputDtoToModel(UserInputDto userInputDto){
        User u = new User();
        u.setUsername(userInputDto.getUsername());
        u.setDescription(userInputDto.getDescription());
        u.setPassword(userInputDto.getPassword());
        u.setListOfFavorites(userInputDto.getListOfFavorites());
        u.setAuthorities(userInputDto.getAuthorities());
        u.setListOfComments(userInputDto.getListOfComments());
        u.setEmail(userInputDto.getEmail());
        return u;
    }

    public static UserOutputDto fromModelToOutputDto(User user){
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.setUsername(user.getUsername());
        userOutputDto.setEmail(user.getEmail());
        userOutputDto.setDescription(user.getDescription());
        userOutputDto.setAuthorities(user.getAuthorities());
        userOutputDto.setListOfComments(user.getListOfComments());
        userOutputDto.setListOfFavorites(user.getListOfFavorites());
        return userOutputDto;
    }

    public static UserSelfProfileDto authenticatedFromUserToSelfProfile(User user){
        UserSelfProfileDto userSelfProfileDto = new UserSelfProfileDto();
        userSelfProfileDto.setUsername(user.getUsername());
        userSelfProfileDto.setDescription(user.getDescription());
        userSelfProfileDto.setListOfComments(user.getListOfComments());
        userSelfProfileDto.setListOfFavorites(user.getListOfFavorites());
        userSelfProfileDto.setEmail(user.getEmail());
        userSelfProfileDto.setPassword(user.getPassword());
        return userSelfProfileDto;
    }

    public static UserProfileDto fromUserToUserProfile(User user){
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setDescription(user.getDescription());
        userProfileDto.setListOfComments(user.getListOfComments());
        userProfileDto.setListOfFavorites(user.getListOfFavorites());
        return userProfileDto;
    }

}
