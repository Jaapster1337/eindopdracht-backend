package com.example.eindopdrachtbackend.repository;

import com.example.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername (String username);
}
