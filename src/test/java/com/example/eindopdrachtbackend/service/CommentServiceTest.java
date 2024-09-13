package com.example.eindopdrachtbackend.service;

        import com.example.eindopdrachtbackend.dto.input.CommentInputDto;
        import com.example.eindopdrachtbackend.dto.mapper.CommentMapper;
        import com.example.eindopdrachtbackend.dto.output.CommentOutputDto;
        import com.example.eindopdrachtbackend.exception.RecordNotFoundException;
        import com.example.eindopdrachtbackend.model.Comment;
        import com.example.eindopdrachtbackend.model.Game;
        import com.example.eindopdrachtbackend.model.User;
        import com.example.eindopdrachtbackend.repository.CommentRepository;
        import com.example.eindopdrachtbackend.repository.GameRepository;
        import com.example.eindopdrachtbackend.repository.UserRepository;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.mockito.junit.jupiter.MockitoExtension;

        import java.util.Arrays;
        import java.util.Optional;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private CommentService commentService;

    @InjectMocks
    private CommentMapper commentMapper;

    User user1;
    Game game1;
    Comment comment1;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("Karel");

        game1 = new Game();
        game1.setId(1L);

        comment1 = new Comment();
//        comment1.setUser(1L);
    }

    @Test
    void getAllComments_ShouldReturnListOfComments() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setUser(user1);
        comment2.setUser(user1);
        comment1.setGame(game1);
        comment2.setGame(game1);
        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));

        CommentOutputDto dto1 = new CommentOutputDto();
        CommentOutputDto dto2 = new CommentOutputDto();
//        when(CommentMapper.fromModelToOutputDto(comment1)).thenReturn(dto1);
//        when(CommentMapper.fromModelToOutputDto(comment2)).thenReturn(dto2);

        var result = commentService.getAllComments();
        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void createComment_ShouldSaveAndReturnComment() {
        CommentInputDto inputDto = new CommentInputDto();
        inputDto.setUserId("Karel");
        inputDto.setGameId(1L);
        Comment comment = new Comment();
        comment.setUser(user1);
        comment.setGame(game1);
        CommentOutputDto outputDto = new CommentOutputDto();

        when(userRepository.findUserByUsername("Karel")).thenReturn(Optional.of(user1));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game1));
//        when(CommentMapper.fromInputDtoToModel(inputDto)).thenReturn(comment);
        when(commentRepository.save(any())).thenReturn(comment);
//        when(CommentMapper.fromModelToOutputDto(comment)).thenReturn(outputDto);

        var result = commentService.createComment(inputDto);
        assertEquals("Karel", result.getUserId());
//        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void getCommentById_ShouldReturnComment_WhenCommentExists() {
        long id = 1L;
        CommentOutputDto outputDto = new CommentOutputDto();
        outputDto.setUserId("Karel");
        outputDto.setGameId(1L);
        Comment comment1 = new Comment();
        comment1.setUser(user1);
        comment1.setGame(game1);

        when(commentRepository.findById(id)).thenReturn(Optional.of(comment1));
        //when(CommentMapper.fromModelToOutputDto(comment1)).thenReturn(outputDto);

        var result = commentService.getCommentById(id);
        assertEquals(outputDto.getUserId(), result.getUserId());
        verify(commentRepository, times(1)).findById(id);
    }

    @Test
    void getCommentById_ShouldThrowException_WhenCommentDoesNotExist() {
        long id = 1L;

        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.getCommentById(id));
        verify(commentRepository, times(1)).findById(id);
    }

    @Test
    void updateComment_ShouldUpdateAndReturnComment_WhenCommentExists() {
        long id = 1L;
        CommentInputDto inputDto = new CommentInputDto();
        Comment existingComment = new Comment();
        User user = new User();
        Game game = new Game();
        CommentOutputDto outputDto = new CommentOutputDto();

        inputDto.setUserId("Henk");
        inputDto.setGameId(2L);

        when(commentRepository.findById(id)).thenReturn(Optional.of(existingComment));
        when(userRepository.findUserByUsername(inputDto.getUserId())).thenReturn(Optional.of(user));
        when(gameRepository.findById(inputDto.getGameId())).thenReturn(Optional.of(game));
        //when(CommentMapper.fromModelToOutputDto(existingComment)).thenReturn(outputDto);

        var result = commentService.updateComment(id, inputDto);
        assertEquals(outputDto.getUserId(), result.getUserId());
        verify(commentRepository, times(1)).save(existingComment);
    }

    @Test
    void updateComment_ShouldThrowException_WhenCommentDoesNotExist() {
        long id = 1L;
        CommentInputDto inputDto = new CommentInputDto();

        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.updateComment(id, inputDto));
        verify(commentRepository, times(0)).save(any());
    }

    @Test
    void updateComment_ShouldThrowException_WhenUserDoesNotExist() {
        long id = 1L;
        CommentInputDto inputDto = new CommentInputDto();
        inputDto.setUserId("nonexistentUser");

        Comment comment = new Comment();
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        when(userRepository.findUserByUsername(inputDto.getUserId())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.updateComment(id, inputDto));
        verify(commentRepository, times(0)).save(any());
    }

    @Test
    void updateComment_ShouldThrowException_WhenGameDoesNotExist() {
        long id = 1L;
        CommentInputDto inputDto = new CommentInputDto();
        inputDto.setUserId("username");
        inputDto.setGameId(2L);

        Comment comment = new Comment();
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        when(userRepository.findUserByUsername(inputDto.getUserId())).thenReturn(Optional.of(new User()));
        when(gameRepository.findById(inputDto.getGameId())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.updateComment(id, inputDto));
        verify(commentRepository, times(0)).save(any());
    }

    @Test
    void deleteComment_ShouldDeleteComment_WhenCommentExists() {
        long id = 1L;
        Comment comment = new Comment();

        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));

        var result = commentService.deleteComment(id);
        assertEquals("Comment with id " + id + " has been removed", result);
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void deleteComment_ShouldThrowException_WhenCommentDoesNotExist() {
        long id = 1L;

        when(commentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> commentService.deleteComment(id));
        verify(commentRepository, times(0)).delete(any());
    }
}
