package com.example.eindopdrachtbackend.config;

import com.example.eindopdrachtbackend.filter.JwtRequestFilter;
import com.example.eindopdrachtbackend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                                auth
                                        //game
                                        .requestMatchers(HttpMethod.POST, "/games").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET,"/games").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET,"/games/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "games/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/games/likes/*").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/games/*/publisher").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/games/**").hasAnyRole("ADMIN")

                                        //publisher
                                        .requestMatchers(HttpMethod.POST, "/publishers").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/publishers").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/publishers/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/publishers/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/publishers/**").hasAnyRole("ADMIN")

                                        //genre
                                        .requestMatchers(HttpMethod.POST, "/genres").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/genres").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/genres/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/genres/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasAnyRole("ADMIN")

                                        //comment
                                        .requestMatchers(HttpMethod.POST, "/comments").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/comments").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/comments/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/comments/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/genres/**").hasAnyRole("ADMIN")

                                        //user
                                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/users/**").hasAnyRole("ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyRole("ADMIN")

                                        //image
                                        .requestMatchers(HttpMethod.POST, "/images").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/games/*/image").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/publisher/*/image").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/images/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/images/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/images/**").hasAnyRole("ADMIN")

                                        .requestMatchers("/authenticated").authenticated()
                                        .requestMatchers("/authenticate").permitAll()

                                        .anyRequest().denyAll()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}