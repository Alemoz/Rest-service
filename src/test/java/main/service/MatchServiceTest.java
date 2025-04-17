package main.service;

import main.dao.MatchDAO;
import main.dto.MatchDTO;
import main.mapper.MatchMapper;
import main.model.MatchModel;
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
public class MatchServiceTest {
    @Mock
    private MatchDAO matchDAO;

    @Mock
    private MatchMapper matchMapper;

    @InjectMocks
    private MatchService matchService;

    @Test
    void testFindById() throws SQLException {
        Long matchId = 1L;
        MatchModel model = new MatchModel();
        model.setMatchId(matchId);

        MatchDTO dto = new MatchDTO();
        dto.setId(matchId);

        when(matchDAO.findById(matchId)).thenReturn(model);
        when(matchMapper.toDTO(model)).thenReturn(dto);

        MatchDTO result = matchService.findById(matchId);

        assertNotNull(result);
        assertEquals(matchId, result.getId());
        verify(matchDAO).findById(matchId);
        verify(matchMapper).toDTO(model);
    }

    @Test
    void testFindById_notFound() throws SQLException {
        when(matchDAO.findById(100L)).thenReturn(null);

        MatchDTO result = matchService.findById(100L);

        assertNull(result);
        verify(matchDAO).findById(100L);
        verifyNoInteractions(matchMapper);
    }

    @Test
    void testFindAll() throws SQLException {
        MatchModel model = new MatchModel();
        MatchDTO dto = new MatchDTO();

        when(matchDAO.findAll()).thenReturn(Collections.singletonList(model));
        when(matchMapper.toDTO(model)).thenReturn(dto);

        List<MatchDTO> result = matchService.findAll();

        assertEquals(1, result.size());
        verify(matchDAO).findAll();
        verify(matchMapper).toDTO(model);
    }

    @Test
    void testSave() throws SQLException {
        MatchDTO dto = new MatchDTO();
        MatchModel model = new MatchModel();

        when(matchMapper.toModel(dto)).thenReturn(model);

        matchService.save(dto);

        verify(matchDAO).save(model);
    }

    @Test
    void testUpdate() throws SQLException {
        MatchDTO dto = new MatchDTO();
        MatchModel model = new MatchModel();

        when(matchMapper.toModel(dto)).thenReturn(model);

        matchService.update(dto);

        verify(matchDAO).update(model);
    }

    @Test
    void testDelete() throws SQLException {
        Long id = 1L;
        matchService.delete(id);
        verify(matchDAO).delete(id);
    }

    @Test
    void testDelete_nullId_shouldThrowException() throws SQLException {
        assertThrows(NullPointerException.class, () -> matchService.delete(null));
        verify(matchDAO, never()).delete(any());
    }

    @Test
    void testSave_nullDTO_shouldThrowException() throws SQLException {
        assertThrows(NullPointerException.class, () -> matchService.save(null));
        verify(matchDAO, never()).save(any());
    }
}
