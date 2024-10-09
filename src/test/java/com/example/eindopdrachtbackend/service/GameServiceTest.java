package com.example.eindopdrachtbackend.service;

import com.example.eindopdrachtbackend.dto.input.GameInputDto;
import com.example.eindopdrachtbackend.dto.mapper.CommentMapper;
import com.example.eindopdrachtbackend.dto.mapper.GameMapper;
import com.example.eindopdrachtbackend.dto.mapper.GenreMapper;
import com.example.eindopdrachtbackend.dto.output.GameOutputDto;
import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
import com.example.eindopdrachtbackend.model.*;
import com.example.eindopdrachtbackend.repository.CommentRepository;
import com.example.eindopdrachtbackend.repository.GameRepository;
import com.example.eindopdrachtbackend.repository.GenreRepository;
import com.example.eindopdrachtbackend.repository.PublisherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameRepository gameRepository;
    @Mock
    GenreRepository genreRepository;

    @Mock
    PublisherRepository publisherRepository;

    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    GameService gameService;

    @InjectMocks
    CommentMapper commentMapper;

    @InjectMocks
    GameMapper gameMapper;

    @InjectMocks
    GenreMapper genreMapper;

    private List<Genre> genres = new ArrayList<>();
    private GameInputDto gameInputDto;
    private Game game1;
    private Game game2;

    private Publisher publisher1;

    @BeforeEach
    void setUp() {
        // genre setup
        Genre genre = new Genre();
        genre.setName("Simulation");
        genre.setId(1L);

        Genre genre2 = new Genre();
        genre2.setName("Deck builders");
        genre2.setId(2L);

        genres.add(genre);
        genres.add(genre2);

        //user setup
        User user1 = new User();
        user1.setUsername("Henk");

        User user2 = new User();
        user2.setUsername("Violet");

        //publisher setup
        publisher1 = new Publisher();
        publisher1.setId(1l);
        publisher1.setName("Henk");
        publisher1.setCreationDate(LocalDate.parse("2001-09-11"));

        //comments setup
        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setUser(user1);

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setUser(user2);

        List<Comment> commentIds = Arrays.asList(comment1, comment2);
        comment1.setId(1L);

        // game input
        gameInputDto = new GameInputDto();
        gameInputDto.setName("tetris");
        gameInputDto.setPublisherId(1L);
        gameInputDto.setGenreId(Collections.singletonList(1l));
        gameInputDto.setLikes(5L);
        gameInputDto.setListOfComments(commentIds);

        game1 = new Game();
        game1.setId(1l);
        game1.setGenre(List.of(genre));
        game1.setName("tetris");
        game1.setLikes(0l);
        game1.setListOfComments(commentIds);

        game2 = new Game();
        game2.setId(2l);
        game2.setGenre(List.of(genre));
        game2.setName("tetris");
        game2.setLikes(0l);
        game2.setListOfComments(commentIds);

        comment1.setGame(game1);

        comment2.setGame(game2);


    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("should return all games")
    void getAllGames() {
        //arrange
        when(gameRepository.findAll()).thenReturn(List.of(game1, game2));
        when(gameRepository.countUsersWhoFavoritedGame(anyLong())).thenReturn(0);
        //act
        List<GameOutputDto> gamesFound = gameService.getAllGames();

        //assert
        assertEquals(game1.getName(), gamesFound.get(0).getName());
        assertEquals(game2.getName(), gamesFound.get(1).getName());

    }

    @Test
    @DisplayName("should add game")
    void createGame() {
        //arrange
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genres.get(0)));
        when(gameRepository.save(any())).thenReturn(game1);
        when(publisherRepository.findById(any())).thenReturn(Optional.of(publisher1));
        //ACT
        GameOutputDto gameOutputDto = gameService.createGame(gameInputDto);


        //assert
        assertEquals("tetris", gameOutputDto.getName());
        assertEquals("Henk", publisher1.getName());

    }

    @Test
    @DisplayName("Should return game by ID")
    void getGameById() {
        //arrange
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));

        //act
        GameOutputDto gameOutputDto = gameService.getGameById(1L);

        //assert
        assertEquals(game1.getName(), gameOutputDto.getName());
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException for getGameById")
    void getGameById_throwsException() {
        //arrange
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act
//        GameOutputDto gameOutputDto = gameService.getGameById(1L);

        //assert
//        assertThrows(RecordNotFoundException.class );
        assertThrows(RecordNotFoundException.class, () -> gameService.getGameById(1L));
    }

    @Test
    @DisplayName("Should update game")
    void updateGame() {
        //arrange
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher1));
        when(genreRepository.findById(anyLong())).thenReturn(Optional.of(genres.get(0))).thenReturn(Optional.of(genres.get(1)));
        GameInputDto gid = new GameInputDto();
        gid.setName("Dark Souls");
        gid.setPublisherId(2L);
        gid.setGenreId(Arrays.asList(1L, 2L));
        //act
        GameOutputDto gameOutputDto = gameService.updateGame(1L, gid);

        //assert
        assertEquals("Dark Souls", gameOutputDto.getName());
        assertEquals(1L, gameOutputDto.getPublisherId());
        assertEquals(Arrays.asList(1L, 2L), gameOutputDto.getGenreId());
    }

    @Test
    @DisplayName("Should retain existing publisher when the new publisher is not present")
    void updateGame_shouldRetainExistingPublisherWhenNotPresent() {
        // Arrange
        Publisher existingPublisher = new Publisher();
        existingPublisher.setId(1L);
        existingPublisher.setName("Existing Publisher");

        Genre genre1 = new Genre();
        genre1.setId(1L);

        Game existingGame = new Game();
        existingGame.setId(1L);
        existingGame.setName("Old Game");
        existingGame.setLikes(0L);
        existingGame.setGenre(Collections.singletonList(genre1));
        existingGame.setPublisher(existingPublisher);

        GameInputDto gameInputDto = new GameInputDto();
        gameInputDto.setName("Updated Game");
        gameInputDto.setPublisherId(2L); // ID that does not exist
        gameInputDto.setGenreId(new ArrayList<>());
        gameInputDto.setLikes(10L);
        gameInputDto.setListOfComments(new ArrayList<>());
        gameInputDto.setListOfFavorites(new ArrayList<>());

        when(gameRepository.findById(1L)).thenReturn(Optional.of(existingGame)); // Existing game
        when(publisherRepository.findById(2L)).thenReturn(Optional.empty()); // New publisher not found

        // Act
        GameOutputDto result = gameService.updateGame(1L, gameInputDto);

        // Assert
        assertEquals(1L, result.getPublisherId()); // Verify existing publisher is retained
        assertEquals("Updated Game", result.getName());
        assertEquals(10L, result.getLikes());

        verify(gameRepository, times(1)).save(existingGame);
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException instead of updating game")
    void updateGame_shouldThrowRecordNotFoundException() {
        //arrange
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));
        when(genreRepository.findById(2L)).thenReturn(Optional.empty());
        GameInputDto gid = new GameInputDto();
        gid.setName("Dark Souls");
        gid.setPublisherId(2L);
        gid.setGenreId(Arrays.asList(2L));

        //assert
        assertThrows(RecordNotFoundException.class, () -> gameService.updateGame(1L, gid));
    }

    @Test
    @DisplayName("Should delete game")
    void deleteGame() {
        // Arrange
        Game game1 = new Game();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(game1));


        // Act
        String result = gameService.deleteGame(1L);

        // Assert
        verify(gameRepository, times(1)).delete(game1);
        assertEquals("Game with id 1 has been removed", result);
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException instead of deleting game")
    void deleteGame_shouldThrowException() {
        // Arrange
        Game game1 = new Game();
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        //String result = gameService.deleteGame(1L);

        // Assert
        assertThrows(RecordNotFoundException.class, () -> gameService.deleteGame(1L));
    }

    @Test
    @DisplayName("Should assign publisher to game successfully")
    void assignPublisherToGame() {
        // Arrange
        Game game = new Game();
        game.setId(1L);

        Publisher publisher = new Publisher();
        publisher.setId(1L);
        publisher.setName("Sample Publisher");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher));

        // Act
        String result = gameService.assignPublisherToGame(1L, 1L);

        // Assert
        assertEquals("Publisher 1 has been added to game 1", result);
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    @DisplayName("Should throw exception if game or publisher not found")
    void assignPublisherToGame_shouldThrowException() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(new Publisher()));

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> gameService.assignPublisherToGame(1L, 1L));
    }


    @Test
    @DisplayName("Should assign comment to game successfully")
    void assignCommentToGame() {
        // Arrange
        Game game = new Game();
        game.setId(1L);
        game.setListOfComments(new ArrayList<>());

        Comment comment = new Comment();
        comment.setId(1L);

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Act
        String result = gameService.assignCommentToGame(1L, 1L);

        // Assert
        assertEquals("Comment 1 has been added to game 1", result);
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    @DisplayName("Should throw exception if game or comment not found")
    void assignCommentToGame_shouldThrowException() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        when(commentRepository.findById(1L)).thenReturn(Optional.of(new Comment()));

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> gameService.assignCommentToGame(1L, 1L));
    }

    @Test
    @DisplayName("Should assign genre to game successfully")
    void assingGenreToGame() {
        // Arrange
        Game game = new Game();
        game.setId(1L);
        game.setGenre(new ArrayList<>());

        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Action");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

        // Act
        String result = gameService.assingGenreToGame(1L, 1L);

        // Assert
        assertEquals("Comment 1 has been added to game 1", result);
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    @DisplayName("Should throw exception if game or genre not found")
    void assingGenreToGame_shouldThrowException() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        when(genreRepository.findById(1L)).thenReturn(Optional.of(new Genre()));

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> gameService.assingGenreToGame(1L, 1L));
    }

    @Test
    @DisplayName("Should add image to game successfully")
    void addImageToGame() {
        // Arrange
        Game game = new Game();
        game.setId(1L);
        game.setGenre(new ArrayList<>());

        Image image = new Image();
        image.setId(1L);
        image.setUrl("http://example.com/image.jpg");

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));


        // Act
        GameOutputDto result = gameService.addImageToGame(1L, image);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(image.getUrl(), result.getGameCover().getUrl());
        verify(gameRepository, times(1)).save(game);
    }

    @Test
    @DisplayName("Should return total amount of games")
    void getGameCount_shouldReturnTotalGames() {
        //arrange
        when(gameService.getGameCount()).thenReturn(4L);
        //act
        long totalNumberOfGames = gameService.getGameCount();

        //assert
        assertEquals(4, totalNumberOfGames);
    }

    @Test
    @DisplayName("Should throw exception if game not found when adding image")
    void addImageToGame_shouldThrowException() {
        // Arrange
        Image image = new Image();
        image.setId(1L);
        image.setUrl("http://example.com/image.jpg");

        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            gameService.addImageToGame(1L, image);
        });

        assertEquals("Game with id 1 not found", exception.getMessage());
        verify(gameRepository, times(0)).save(any(Game.class)); // Verify save is not called
    }

    @Test
    @DisplayName("Should successfully increment likes for a game")
    void updateLikes_shouldIncrementLikes() {
        // Arrange
        Game game = new Game();
        game.setId(1L);
        game.setLikes(5L); // Starting with 5 likes

        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        // Act
        String result = gameService.updateLikes(1L);

        // Assert
        assertNotNull(result);
        assertEquals(6L, game.getLikes());  // Likes should be incremented to 6
        assertEquals("Game with id 1 now has 6 likes", result);  // Check the success message
        verify(gameRepository, times(1)).save(game);  // Verify the game was saved
    }

    @Test
    @DisplayName("Should throw exception if game not found when updating likes")
    void updateLikes_shouldThrowExceptionIfGameNotFound() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            gameService.updateLikes(1L);
        });

        assertEquals("No game with id 1 found", exception.getMessage());
        verify(gameRepository, times(0)).save(any(Game.class));
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException when game is not found")
    void updateGame_shouldThrowExceptionWhenGameNotFound() {
        // Arrange
        GameInputDto gameInputDto = new GameInputDto();
        gameInputDto.setName("New Game");
        gameInputDto.setPublisherId(1L);
        gameInputDto.setGenreId(new ArrayList<>());
        gameInputDto.setLikes(5L);
        gameInputDto.setListOfComments(new ArrayList<>());
        gameInputDto.setListOfFavorites(new ArrayList<>());

        // Simulate game not being found
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> {
            gameService.updateGame(1L, gameInputDto);
        });

        // Assert
        assertEquals("No game with id 1 found", exception.getMessage());
        verify(gameRepository, never()).save(any(Game.class)); // Ensure gameRepository.save is not called
    }

}

