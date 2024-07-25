package com.example.eindopdrachtbackend.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSelfProfileDto extends UserProfileDto{
    private String password;
    private String email;

}
