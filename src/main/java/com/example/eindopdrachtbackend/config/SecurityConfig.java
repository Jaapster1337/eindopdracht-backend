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

/*  Deze security is niet de enige manier om het te doen.
    In de andere branch van deze github repo staat een ander voorbeeld
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final CustomUserDetailsService customUserDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    // PasswordEncoderBean. Deze kun je overal in je applicatie injecteren waar nodig.
    // Je kunt dit ook in een aparte configuratie klasse zetten.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Authenticatie met customUserDetailsService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }




    // Authorizatie met jwt
    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                                auth
                                        // Wanneer je deze uncomments, staat je hele security open. Je hebt dan alleen nog een jwt nodig.
                                        //game
                                        .requestMatchers(HttpMethod.POST, "/games").hasAnyRole("NORMAL_USER","ADMIN") //creategame post
                                        .requestMatchers(HttpMethod.GET,"/games").hasAnyRole("NORMAL_USER","ADMIN")    //getgamebyid get
                                        .requestMatchers(HttpMethod.GET,"/games/**").hasAnyRole("NORMAL_USER","ADMIN") //getallgames get
                                        .requestMatchers(HttpMethod.PUT, "games/**").hasAnyRole("NORMAL_USER","ADMIN")   //updategames put
                                        .requestMatchers(HttpMethod.PUT, "/games/likes/*").hasAnyRole("ADMIN")    //updatelikes put
                                        .requestMatchers(HttpMethod.PUT, "/games/*/publisher").hasAnyRole("ADMIN")//assignpublishertogame put
                                        .requestMatchers(HttpMethod.DELETE, "/games/**").hasAnyRole("ADMIN")// deletegame
                                        //publisher
                                        .requestMatchers(HttpMethod.POST, "/publishers").hasAnyRole("NORMAL_USER","ADMIN") //createpublisher
                                        .requestMatchers(HttpMethod.GET, "/publishers").hasAnyRole("NORMAL_USER","ADMIN")  //getall
                                        .requestMatchers(HttpMethod.GET, "/publishers/**").hasAnyRole("NORMAL_USER","ADMIN")//getbyid
                                        .requestMatchers(HttpMethod.PUT, "/publishers/**").hasAnyRole("NORMAL_USER","ADMIN")    //update
                                        .requestMatchers(HttpMethod.DELETE, "/publishers/**").hasAnyRole("ADMIN")//delete
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
                                        .requestMatchers(HttpMethod.PUT, "/games/*/images").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.PUT, "/publisher/*/image").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.GET, "/images/**").hasAnyRole("NORMAL_USER","ADMIN")
                                        .requestMatchers(HttpMethod.DELETE, "/images/**").hasAnyRole("ADMIN")

                                        .requestMatchers("/authenticated").authenticated()
                                        .requestMatchers("/authenticate").permitAll()

                                        // Deny any other requests
                                        .anyRequest().denyAll()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}