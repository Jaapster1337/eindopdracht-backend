package com.example.eindopdrachtbackend.repository;

import com.example.eindopdrachtbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
