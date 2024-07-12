package com.example.eindopdrachtbackend.repository;

import com.example.eindopdrachtbackend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
