package main.service;

import main.dao.GameMapDAO;
import main.dto.GameMapDTO;
import main.mapper.GameMapMapper;
import main.model.GameMapModel;
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
public class GameMapServiceTest {
    @Mock
    private GameMapDAO gameMapDAO;

    @Mock
    private GameMapMapper gameMapMapper;

    @InjectMocks
    private GameMapService gameMapService;

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;
        GameMapModel model = new GameMapModel();
        model.setGameMapId(id);

        GameMapDTO dto = new GameMapDTO();
        dto.setId(id);

        when(gameMapDAO.findById(id)).thenReturn(model);
        when(gameMapMapper.toDTO(model)).thenReturn(dto);

        GameMapDTO result = gameMapService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(gameMapDAO).findById(id);
        verify(gameMapMapper).toDTO(model);
    }

    @Test
    void testFindById_notFound_returnsNull() throws SQLException {
        when(gameMapDAO.findById(anyLong())).thenReturn(null);

        GameMapDTO result = gameMapService.findById(99L);

        assertNull(result);
        verify(gameMapDAO).findById(99L);
        verifyNoInteractions(gameMapMapper);
    }

    @Test
    void testFindAll() throws SQLException {
        GameMapModel model = new GameMapModel();
        GameMapDTO dto = new GameMapDTO();

        when(gameMapDAO.findAll()).thenReturn(Arrays.asList(model));
        when(gameMapMapper.toDTO(model)).thenReturn(dto);

        List<GameMapDTO> result = gameMapService.findAll();

        assertEquals(1, result.size());
        verify(gameMapDAO).findAll();
        verify(gameMapMapper).toDTO(model);
    }

    @Test
    void testFindAll_empty() throws SQLException {
        when(gameMapDAO.findAll()).thenReturn(Collections.emptyList());

        List<GameMapDTO> result = gameMapService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(gameMapDAO).findAll();
        verifyNoInteractions(gameMapMapper);
    }

    @Test
    void testSave() throws SQLException {
        GameMapDTO dto = new GameMapDTO();
        dto.setName("New map");

        GameMapModel model = new GameMapModel();
        model.setName("New map");

        when(gameMapMapper.toModel(dto)).thenReturn(model);

        gameMapService.save(dto);

        verify(gameMapDAO).save(model);
    }

    @Test
    void testSave_nullDTO_throwsException() throws SQLException {
        assertThrows(NullPointerException.class, () -> gameMapService.save(null));
        verify(gameMapDAO, never()).save(any());
    }

    @Test
    void testUpdate() throws SQLException {
        GameMapDTO dto = new GameMapDTO();
        GameMapModel model = new GameMapModel();

        when(gameMapMapper.toModel(dto)).thenReturn(model);

        gameMapService.update(dto);

        verify(gameMapDAO).update(model);
    }

    @Test
    void testDelete() throws SQLException {
        Long id = 1L;
        gameMapService.delete(id);

        verify(gameMapDAO).delete(id);
    }

    @Test
    void testDelete_nullId_throwsException() throws SQLException {
        assertThrows(NullPointerException.class, () -> gameMapService.delete(null));
        verify(gameMapDAO, never()).delete(any());
    }
}
