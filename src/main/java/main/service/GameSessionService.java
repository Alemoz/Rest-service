package main.service;

import main.dao.GameSessionDAO;
import main.dto.GameSessionDTO;
import main.mapper.GameSessionMapper;
import main.model.GameSessionModel;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class GameSessionService {

    private final GameSessionDAO gameSessionDAO;
    private final GameSessionMapper gameSessionMapper;

    public GameSessionService(GameSessionDAO gameSessionDAO, GameSessionMapper gameSessionMapper) {
        this.gameSessionDAO = gameSessionDAO;
        this.gameSessionMapper = gameSessionMapper;
    }

    public GameSessionService() {
        this.gameSessionDAO = new GameSessionDAO();
        this.gameSessionMapper = GameSessionMapper.INSTANCE;
    }

    public List<GameSessionDTO> findAll() throws SQLException {
        List<GameSessionModel> gameSessions = gameSessionDAO.findAll();
        return gameSessions.stream()
                .map(gameSessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GameSessionDTO findById(Long id) throws SQLException {
        GameSessionModel gameSession = gameSessionDAO.findById(id);
        if (gameSession != null) {
            return gameSessionMapper.toDTO(gameSession);
        }
        return null;
    }

    public void save(GameSessionDTO gameSessionDTO) throws SQLException {
        if(gameSessionDTO == null){
            throw new NullPointerException("GameSessionDTO cannot be null");
        }
        GameSessionModel gameSessionModel = gameSessionMapper.toModel(gameSessionDTO);
        gameSessionDAO.save(gameSessionModel);
    }

    public void update(GameSessionDTO gameSessionDTO) throws SQLException {
        if(gameSessionDTO == null){
            throw new NullPointerException("GameSessionDTO cannot be null");
        }
        GameSessionModel gameSessionModel = gameSessionMapper.toModel(gameSessionDTO);
        gameSessionDAO.update(gameSessionModel);
    }

    public void delete(Long id) throws SQLException {
        if(id == null){
            throw new NullPointerException("ID cannot be null");
        }
        gameSessionDAO.delete(id);
    }
}
