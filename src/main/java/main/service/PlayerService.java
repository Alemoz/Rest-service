package main.service;

import main.dao.GameDAO;
import main.dao.PlayerDAO;
import main.dto.PlayerDTO;
import main.mapper.PlayerMapper;
import main.model.PlayerModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import static main.mapper.PlayerMapper.*;

public class PlayerService {
    private final PlayerDAO dao;
    private final PlayerMapper mapper;

    public PlayerService(PlayerDAO dao, PlayerMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    public PlayerService() {
        this.dao = new PlayerDAO();
        this.mapper = PlayerMapper.INSTANCE;
    }

    public void create(PlayerDTO dto) throws SQLException {
        PlayerModel model = mapper.toModel(dto);
        dao.save(model);
        dto.setId(model.getPlayerId());
    }

    public List<PlayerDTO> findAll() throws SQLException {
        List<PlayerModel> models = dao.findAll();
        if (models.isEmpty()) {
            System.out.println("No players found in DAO!");
        }
        return models.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public PlayerDTO findById(Long id) throws SQLException {
        PlayerModel player = dao.findById(id);
        if (player != null) {
            return mapper.toDTO(player);
        }
        return null;
    }

    public void update(PlayerDTO dto) throws SQLException {
        PlayerModel model = mapper.toModel(dto);
        dao.update(model);
    }

    public void delete(long id) throws SQLException {
        dao.delete(id);
    }

}
