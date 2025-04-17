package main.service;

import main.dao.MatchStatDAO;
import main.dto.MatchStatDTO;
import main.mapper.MatchStatMapper;
import main.model.MatchStatModel;
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
public class MatchStatServiceTest {
    @Mock
    private MatchStatDAO matchStatDAO;

    @Mock
    private MatchStatMapper matchStatMapper;

    @InjectMocks
    private MatchStatService matchStatService;

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;

        MatchStatModel model = new MatchStatModel();
        model.setKills(10);
        model.setDeaths(2);
        model.setAssists(5);
        model.setScore(1000);

        MatchStatDTO dto = new MatchStatDTO();
        dto.setId(id);
        dto.setKills(10);
        dto.setDeaths(2);
        dto.setAssists(5);
        dto.setScore(1000);

        when(matchStatDAO.findById(id)).thenReturn(model);
        when(matchStatMapper.toDTO(model)).thenReturn(dto);

        MatchStatDTO result = matchStatService.findById(id);

        assertNotNull(result);
        assertEquals(10, result.getKills());
        verify(matchStatDAO, times(1)).findById(id);
        verify(matchStatMapper, times(1)).toDTO(model);
    }

    @Test
    void testFindAll() throws SQLException {
        MatchStatModel model1 = new MatchStatModel();
        model1.setKills(10);
        model1.setDeaths(1);

        MatchStatModel model2 = new MatchStatModel();
        model2.setKills(5);
        model2.setDeaths(4);

        MatchStatDTO dto1 = new MatchStatDTO();
        dto1.setKills(10);
        dto1.setDeaths(1);

        MatchStatDTO dto2 = new MatchStatDTO();
        dto2.setKills(5);
        dto2.setDeaths(4);

        when(matchStatDAO.findAll()).thenReturn(Arrays.asList(model1, model2));
        when(matchStatMapper.toDTO(model1)).thenReturn(dto1);
        when(matchStatMapper.toDTO(model2)).thenReturn(dto2);

        List<MatchStatDTO> result = matchStatService.findAll();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getKills());
        assertEquals(5, result.get(1).getKills());
        verify(matchStatDAO, times(1)).findAll();
    }

    @Test
    void testSave() throws SQLException {
        MatchStatDTO dto = new MatchStatDTO();
        dto.setKills(8);

        MatchStatModel model = new MatchStatModel();
        model.setKills(8);

        when(matchStatMapper.toModel(dto)).thenReturn(model);

        matchStatService.save(dto);

        verify(matchStatDAO, times(1)).save(model);
    }

    @Test
    void testUpdate() throws SQLException {
        MatchStatDTO dto = new MatchStatDTO();
        dto.setId(1L);
        dto.setScore(1500);

        MatchStatModel model = new MatchStatModel();
        model.setScore(1500);

        when(matchStatMapper.toModel(dto)).thenReturn(model);

        matchStatService.update(dto);

        verify(matchStatDAO, times(1)).update(model);
    }

    @Test
    void testDelete() throws SQLException {
        Long id = 1L;

        matchStatService.delete(id);

        verify(matchStatDAO, times(1)).delete(id);
    }

    @Test
    void testFindById_NotFound() throws SQLException {
        Long id = 999L;

        when(matchStatDAO.findById(id)).thenReturn(null);

        MatchStatDTO result = matchStatService.findById(id);

        assertNull(result);
        verify(matchStatDAO, times(1)).findById(id);
        verify(matchStatMapper, never()).toDTO(any());
    }

    @Test
    void testFindAll_EmptyList() throws SQLException {
        when(matchStatDAO.findAll()).thenReturn(Collections.emptyList());

        List<MatchStatDTO> result = matchStatService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(matchStatDAO, times(1)).findAll();
        verify(matchStatMapper, never()).toDTO(any());
    }

    @Test
    void testSave_NullDTO() throws SQLException {
        assertThrows(NullPointerException.class, () -> matchStatService.save(null));
        verify(matchStatDAO, never()).save(any());
    }

    @Test
    void testUpdate_NullDTO() throws SQLException {
        assertThrows(NullPointerException.class, () -> matchStatService.update(null));
        verify(matchStatDAO, never()).update(any());
    }

    @Test
    void testDelete_NullId() throws SQLException {
        assertThrows(NullPointerException.class, () -> matchStatService.delete(null));
        verify(matchStatDAO, never()).delete(any());
    }
}
