package com.example.eindopdrachtbackend.repository;

import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT COUNT(u) FROM Game g JOIN g.listOfFavorites u WHERE g.id = :gameId")
    int countUsersWhoFavoritedGame(@Param("gameId") long gameId);
}
