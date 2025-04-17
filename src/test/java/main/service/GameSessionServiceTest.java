package main.service;

import main.dao.GameSessionDAO;
import main.dto.GameSessionDTO;
import main.mapper.GameSessionMapper;
import main.model.GameSessionModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameSessionServiceTest {
    @Mock
    private GameSessionDAO gameSessionDAO;

    @Mock
    private GameSessionMapper gameSessionMapper;

    @InjectMocks
    private GameSessionService gameSessionService;

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;
        GameSessionModel model = new GameSessionModel();
        model.setGameSessionId(id);

        GameSessionDTO dto = new GameSessionDTO();
        dto.setId(id);

        when(gameSessionDAO.findById(id)).thenReturn(model);
        when(gameSessionMapper.toDTO(model)).thenReturn(dto);

        GameSessionDTO result = gameSessionService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(gameSessionDAO).findById(id);
        verify(gameSessionMapper).toDTO(model);
    }

    @Test
    void testFindById_notFound() throws SQLException {
        Long id = 100L;
        when(gameSessionDAO.findById(id)).thenReturn(null);

        GameSessionDTO result = gameSessionService.findById(id);

        assertNull(result);
        verify(gameSessionDAO).findById(id);
        verifyNoInteractions(gameSessionMapper);
    }

    @Test
    void testFindAll() throws SQLException {
        GameSessionModel model = new GameSessionModel();
        GameSessionDTO dto = new GameSessionDTO();

        when(gameSessionDAO.findAll()).thenReturn(Collections.singletonList(model));
        when(gameSessionMapper.toDTO(model)).thenReturn(dto);

        List<GameSessionDTO> result = gameSessionService.findAll();

        assertEquals(1, result.size());
        verify(gameSessionDAO).findAll();
        verify(gameSessionMapper).toDTO(model);
    }

    @Test
    void testFindAll_emptyList() throws SQLException {
        when(gameSessionDAO.findAll()).thenReturn(Collections.emptyList());

        List<GameSessionDTO> result = gameSessionService.findAll();

        assertTrue(result.isEmpty());
        verify(gameSessionDAO).findAll();
        verifyNoInteractions(gameSessionMapper);
    }

    @Test
    void testSave() throws SQLException {
        GameSessionDTO dto = new GameSessionDTO();
        GameSessionModel model = new GameSessionModel();

        when(gameSessionMapper.toModel(dto)).thenReturn(model);

        gameSessionService.save(dto);

        verify(gameSessionDAO).save(model);
    }

    @Test
    void testSave_nullDTO_shouldThrowException() throws SQLException{
        assertThrows(NullPointerException.class, () -> gameSessionService.save(null));
        verify(gameSessionDAO, never()).save(any());
    }

    @Test
    void testUpdate() throws SQLException {
        GameSessionDTO dto = new GameSessionDTO();
        GameSessionModel model = new GameSessionModel();

        when(gameSessionMapper.toModel(dto)).thenReturn(model);

        gameSessionService.update(dto);

        verify(gameSessionDAO).update(model);
    }

    @Test
    void testDelete() throws SQLException {
        Long id = 1L;
        gameSessionService.delete(id);

        verify(gameSessionDAO).delete(id);
    }

    @Test
    void testDelete_nullId_shouldThrowException() throws SQLException{
        assertThrows(NullPointerException.class, () -> gameSessionService.delete(null));
        verify(gameSessionDAO, never()).delete(any());
    }

}
