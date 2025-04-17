package main.service;

import main.dao.PlayerDAO;
import main.dto.PlayerDTO;
import main.mapper.PlayerMapper;
import main.model.PlayerModel;
import org.junit.jupiter.api.BeforeEach;
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
class PlayerServiceTest {

    @Mock
    private PlayerDAO playerDAO;

    @Mock
    private PlayerMapper playerMapper;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp(){
        playerDAO = mock(PlayerDAO.class);
        playerMapper = mock(PlayerMapper.class);
        playerService = new PlayerService(playerDAO, playerMapper);
    }
    @Test
    void testFindAllPlayers() throws SQLException {
        PlayerModel player = new PlayerModel();
        player.setPlayerId(1L);
        player.setUsername("test");
        player.setEmail("test@mail.com");

        PlayerDTO dto = new PlayerDTO();
        dto.setId(1L);
        dto.setUsername("test");
        dto.setEmail("test@mail.com");

        when(playerDAO.findAll()).thenReturn(Collections.singletonList(player));
        when(playerMapper.toDTO(player)).thenReturn(dto);

        List<PlayerDTO> result = playerService.findAll();

        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getUsername());
        verify(playerDAO, times(1)).findAll();
        verify(playerMapper, times(1)).toDTO(player);
    }

    @Test
    void testFindById() throws SQLException {
        Long playerId = 1L;

        PlayerModel player = new PlayerModel();
        player.setPlayerId(playerId);
        player.setUsername("test");
        player.setEmail("test@mail.com");

        PlayerDTO dto = new PlayerDTO();
        dto.setId(playerId);
        dto.setUsername("test");
        dto.setEmail("test@mail.com");

        when(playerDAO.findById(playerId)).thenReturn(player);

        when(playerMapper.toDTO(player)).thenReturn(dto);

        PlayerDTO result = playerService.findById(playerId);

        assertNotNull(result);
        assertEquals(playerId, result.getId());
        assertEquals("test", result.getUsername());
        assertEquals("test@mail.com", result.getEmail());

        verify(playerDAO, times(1)).findById(playerId);
        verify(playerMapper, times(1)).toDTO(player);
    }

    @Test
    void testSavePlayer() throws SQLException {
        PlayerDTO dto = new PlayerDTO();
        dto.setUsername("savedPlayer");
        dto.setEmail("saved@mail.com");

        PlayerModel model = new PlayerModel();
        model.setUsername("savedPlayer");
        model.setEmail("saved@mail.com");

        when(playerMapper.toModel(dto)).thenReturn(model);

        playerService.create(dto);

        verify(playerDAO, times(1)).save(model);
    }

    @Test
    void testDeletePlayer() throws SQLException {
        playerService.delete(1L);
        verify(playerDAO).delete(1L);
    }
}
