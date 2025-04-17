package main.service;

import main.dao.TeamDAO;
import main.dto.TeamDTO;
import main.mapper.TeamMapper;
import main.model.TeamModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @Mock
    private TeamDAO teamDAO;
    @Mock
    private TeamMapper teamMapper;
    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp(){
        teamDAO = mock(TeamDAO.class);
        teamMapper = mock(TeamMapper.class);
        teamService = new TeamService(teamDAO, teamMapper);
    }

    @Test
    void testCreate() throws SQLException {
        TeamDTO dto = new TeamDTO();
        dto.setName("Team Alpha");

        TeamModel model = new TeamModel();
        model.setName("Team Alpha");

        when(teamMapper.toModel(dto)).thenReturn(model);

        teamService.create(dto);

        assertNotNull(model.getCreated_at());

        verify(teamDAO, times(1)).save(model);
    }

    @Test
    void testFindById() throws SQLException {
        Long id = 1L;

        TeamModel model = new TeamModel();
        model.setTeamId(id);
        model.setName("Team Alpha");

        TeamDTO dto = new TeamDTO();
        dto.setId(id);
        dto.setName("Team Alpha");

        when(teamDAO.findById(id)).thenReturn(model);
        when(teamMapper.toDTO(model)).thenReturn(dto);

        TeamDTO result = teamService.findById(id);

        assertNotNull(result);
        assertEquals("Team Alpha", result.getName());
        verify(teamDAO, times(1)).findById(id);
        verify(teamMapper, times(1)).toDTO(model);
    }

    @Test
    void testFindAll() throws SQLException {
        TeamModel model1 = new TeamModel();
        model1.setTeamId(1L);
        model1.setName("Team A");

        TeamModel model2 = new TeamModel();
        model2.setTeamId(2L);
        model2.setName("Team B");

        TeamDTO dto1 = new TeamDTO();
        dto1.setId(1L);
        dto1.setName("Team A");

        TeamDTO dto2 = new TeamDTO();
        dto2.setId(2L);
        dto2.setName("Team B");

        when(teamDAO.findAll()).thenReturn(Arrays.asList(model1, model2));
        when(teamMapper.toDTO(model1)).thenReturn(dto1);
        when(teamMapper.toDTO(model2)).thenReturn(dto2);

        List<TeamDTO> result = teamService.findAll();

        assertEquals(2, result.size());
        assertEquals("Team A", result.get(0).getName());
        assertEquals("Team B", result.get(1).getName());
    }

    @Test
    void testUpdate() throws SQLException {
        TeamDTO dto = new TeamDTO();
        dto.setId(1L);
        dto.setName("Updated Team");

        TeamModel model = new TeamModel();
        model.setTeamId(1L);
        model.setName("Updated Team");

        when(teamMapper.toModel(dto)).thenReturn(model);

        teamService.update(dto);

        verify(teamDAO, times(1)).update(model);
    }

    @Test
    void testDelete() throws SQLException {
        Long id = 1L;
        teamService.delete(id);
        verify(teamDAO, times(1)).delete(id);
    }
}
