package main.service;

import main.dao.GameMapDAO;
import main.dto.GameMapDTO;
import main.mapper.GameMapMapper;
import main.mapper.GameMapper;
import main.model.GameMapModel;
import main.model.GameModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GameMapService {

    private final GameMapDAO gameMapDAO;
    private final GameMapMapper gameMapMapper;

    public GameMapService(GameMapDAO gameMapDAO, GameMapMapper gameMapMapper) {
        this.gameMapDAO = gameMapDAO;
        this.gameMapMapper = gameMapMapper;
    }
    public GameMapService() {
        this.gameMapDAO = new GameMapDAO();
        this.gameMapMapper = GameMapMapper.INSTANCE;
    }

    public List<GameMapDTO> findAll() throws SQLException {
        List<GameMapModel> gameMaps = gameMapDAO.findAll();
        return gameMaps.stream()
                .map(gameMapMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GameMapDTO findById(Long id) throws SQLException {
        GameMapModel gameMap = gameMapDAO.findById(id);
        if (gameMap != null) {
            return gameMapMapper.toDTO(gameMap);
        }
        return null;
    }

    public void save(GameMapDTO gameMapDTO) throws SQLException {
        if(gameMapDTO == null){
            throw new NullPointerException("gameMapDTO cannot be null");
        }
        GameMapModel model = new GameMapModel();
        model.setName(gameMapDTO.getName());
        model.setLocation(gameMapDTO.getLocation());

        GameModel game = new GameModel();
        game.setGameId(gameMapDTO.getGameId());
        model.setGame(game);
        gameMapDAO.save(model);
    }

    public void update(GameMapDTO gameMapDTO) throws SQLException {
        if(gameMapDTO == null){
            throw new NullPointerException("gameMapDTO cannot be null");
        }
        GameMapModel model = new GameMapModel();
        model.setGameMapId(gameMapDTO.getId());
        model.setName(gameMapDTO.getName());
        model.setLocation(gameMapDTO.getLocation());

        GameModel game = new GameModel();
        game.setGameId(gameMapDTO.getGameId());
        model.setGame(game);
        gameMapDAO.update(model);
    }

    public void delete(Long id) throws SQLException {
        if(id == null){
            throw new NullPointerException("id cannot be null");
        }
        gameMapDAO.delete(id);
    }
}
