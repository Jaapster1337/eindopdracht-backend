package com.example.eindopdrachtbackend.service;


import com.example.eindopdrachtbackend.dto.output.UserOutputDto;
import com.example.eindopdrachtbackend.model.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    public void CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

//    @Autowired
//    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserOutputDto userOutputDto = userService.getUserByUsername(username);


        String password = userOutputDto.getPassword();

        Set<Authority> authorities = userOutputDto.getAuthorities();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority: authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }

        return new org.springframework.security.core.userdetails.User(username, password, grantedAuthorities);
    }

}
