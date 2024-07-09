package com.example.eindopdrachtbackend.repository;

import com.example.eindopdrachtbackend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
