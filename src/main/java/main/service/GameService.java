package main.service;

import main.dao.GameDAO;
import main.dto.GameDTO;
import main.mapper.GameMapper;
import main.model.GameModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GameService {

    private final GameDAO gameDAO;
    private final GameMapper mapper;

    public GameService(GameDAO dao, GameMapper mapper) {
        this.gameDAO = dao;
        this.mapper = mapper;
    }
    public GameService() {
        this.gameDAO = new GameDAO();
        this.mapper = GameMapper.INSTANCE;
    }

    public List<GameDTO> findAll() throws SQLException {
        List<GameModel> games = gameDAO.findAll();
        return games.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public GameDTO findById(Long id) throws SQLException {
        GameModel game = gameDAO.findById(id);
        if (game != null) {
            return mapper.toDTO(game);
        }
        return null;
    }

    public void save(GameDTO gameDTO) throws SQLException {
        if (gameDTO == null){
            throw new NullPointerException("GameDTO cannot be null");
        }
        GameModel gameModel = mapper.toModel(gameDTO);
        gameDAO.save(gameModel);
    }

    public void update(GameDTO gameDTO) throws SQLException {
        if (gameDTO == null){
                    throw new NullPointerException("GameDTO cannot be null");
        }
        GameModel gameModel = mapper.toModel(gameDTO);
        gameDAO.update(gameModel);
    }

    public void delete(Long id) throws SQLException {
        if (id == null){
                    throw new NullPointerException("GameDTO cannot be null");
        }
        gameDAO.delete(id);
    }
}
