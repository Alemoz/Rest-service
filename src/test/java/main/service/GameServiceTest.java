package main.service;

import main.dao.GameDAO;
import main.dto.GameDTO;
import main.mapper.GameMapper;
import main.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Mock
    private GameDAO gameDAO;

    @Mock
    private GameMapper gameMapper;
    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        gameDAO = mock(GameDAO.class);
        gameMapper = mock(GameMapper.class);
        gameService = new GameService(gameDAO, gameMapper);
    }

    @Test
    void testFindAll() throws SQLException {
        GameModel model = new GameModel();
        model.setGameId(1L);
        model.setTitle("Test Game");

        GameDTO dto = new GameDTO();
        dto.setId(1L);
        dto.setTitle("Test Game");

        when(gameDAO.findAll()).thenReturn(Arrays.asList(model));
        when(gameMapper.toDTO(model)).thenReturn(dto);

        List<GameDTO> result = gameService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test Game", result.get(0).getTitle());
    }

    @Test
    void testCreate() throws SQLException {
        GameDTO dto = new GameDTO();
        dto.setTitle("New game");
        dto.setGenre("New game genre");

        GameModel model = new GameModel();
        model.setTitle("New game");
        model.setGenre("New game genre");

        when(gameMapper.toModel(dto)).thenReturn(model);

        gameService.save(dto);

        verify(gameDAO, times(1)).save(model);
    }

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;

        GameModel model = new GameModel();
        model.setGameId(id);
        model.setTitle("Some Game");

        GameDTO dto = new GameDTO();
        dto.setId(id);
        dto.setTitle("Some Game");

        when(gameDAO.findById(id)).thenReturn(model);

        when(gameMapper.toDTO(model)).thenReturn(dto);

        GameDTO result = gameService.findById(id);

        assertNotNull(result);
        assertEquals("Some Game", result.getTitle());

        verify(gameDAO, times(1)).findById(id);
    }

    @Test
    void testUpdate() throws SQLException {
        GameDTO dto = new GameDTO();
        dto.setId(1L);  // Устанавливаем ID
        dto.setTitle("Updated Game");

        GameModel model = new GameModel();
        model.setGameId(1L);  // Устанавливаем ID в модель
        model.setTitle("Updated Game");

        when(gameMapper.toModel(dto)).thenReturn(model);  // Мокируем преобразование

        gameService.update(dto);  // Вызываем сервис

        verify(gameDAO, times(1)).update(model);  // Проверяем, что update был вызван с нужными параметрами
    }

    @Test
    void testDelete() throws SQLException {
        gameService.delete(3L);
        verify(gameDAO, times(1)).delete(3L);
    }

    @Test
    void testFindById_ValidId_ReturnsDTO() throws SQLException {
        Long id = 1L;

        GameModel model = new GameModel();
        model.setGameId(id);
        model.setTitle("Game Title");

        GameDTO dto = new GameDTO();
        dto.setId(id);
        dto.setTitle("Game Title");

        when(gameDAO.findById(id)).thenReturn(model);
        when(gameMapper.toDTO(model)).thenReturn(dto);

        GameDTO result = gameService.findById(id);

        assertNotNull(result);
        assertEquals("Game Title", result.getTitle());
        verify(gameDAO).findById(id);
        verify(gameMapper).toDTO(model);
    }

    @Test
    void testFindById_NotFound_ReturnsNull() throws SQLException {
        Long id = 2L;
        when(gameDAO.findById(id)).thenReturn(null);

        GameDTO result = gameService.findById(id);

        assertNull(result);
        verify(gameDAO).findById(id);
        verify(gameMapper, never()).toDTO(any());
    }

    @Test
    void testFindAll_ReturnsList() throws SQLException {
        GameModel model = new GameModel();
        model.setGameId(1L);
        model.setTitle("Game A");

        GameDTO dto = new GameDTO();
        dto.setId(1L);
        dto.setTitle("Game A");

        when(gameDAO.findAll()).thenReturn(Arrays.asList(model));
        when(gameMapper.toDTO(model)).thenReturn(dto);

        List<GameDTO> result = gameService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Game A", result.get(0).getTitle());

        verify(gameDAO).findAll();
        verify(gameMapper).toDTO(model);
    }

    @Test
    void testFindAll_EmptyList() throws SQLException {
        when(gameDAO.findAll()).thenReturn(Collections.emptyList());

        List<GameDTO> result = gameService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(gameDAO).findAll();
        verify(gameMapper, never()).toDTO(any());
    }

    @Test
    void testSave_ValidDTO() throws SQLException {
        GameDTO dto = new GameDTO();
        dto.setId(1L);
        dto.setTitle("Saved Game");

        GameModel model = new GameModel();
        model.setGameId(1L);
        model.setTitle("Saved Game");

        when(gameMapper.toModel(dto)).thenReturn(model);

        gameService.save(dto);

        verify(gameMapper).toModel(dto);
        verify(gameDAO).save(model);
    }

    @Test
    void testSave_NullDTO_ThrowsException() throws SQLException{
        assertThrows(NullPointerException.class, () -> gameService.save(null));
        verify(gameDAO, never()).save(any());
    }

    @Test
    void testUpdate_ValidDTO() throws SQLException {
        GameDTO dto = new GameDTO();
        dto.setId(1L);
        dto.setTitle("Updated Game");

        GameModel model = new GameModel();
        model.setGameId(1L);
        model.setTitle("Updated Game");

        when(gameMapper.toModel(dto)).thenReturn(model);

        gameService.update(dto);

        verify(gameMapper).toModel(dto);
        verify(gameDAO).update(model);
    }

    @Test
    void testUpdate_NullDTO_ThrowsException() throws SQLException{
        assertThrows(NullPointerException.class, () -> gameService.update(null));
        verify(gameDAO, never()).update(any());
    }

    @Test
    void testDelete_ValidId() throws SQLException {
        Long id = 1L;
        gameService.delete(id);
        verify(gameDAO).delete(id);
    }

    @Test
    void testDelete_NullId_ThrowsException() throws SQLException{
        assertThrows(NullPointerException.class, () -> gameService.delete(null));
        verify(gameDAO, never()).delete(any());
    }
}
