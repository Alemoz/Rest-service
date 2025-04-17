package main.dao;

import main.exception.ErrorHandler;
import main.model.GameModel;
import main.model.GameSessionModel;
import main.model.PlayerModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameSessionDAO {

    public void save(GameSessionModel gameSession) throws SQLException {
        String sql = "INSERT INTO game_sessions (score, session_start, session_end, game_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(gameSession.getSessionStart()));
            stmt.setTimestamp(2, Timestamp.valueOf(gameSession.getSessionEnd()));
            stmt.setInt(3, gameSession.getScore());
            stmt.setLong(4, gameSession.getGame().getGameId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    gameSession.setGameSessionId(generatedKeys.getLong(1));
                }
            }

            addPlayersToSession(gameSession);
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public GameSessionModel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM game_sessions WHERE game_session_id = ?";
        GameSessionModel gameSession = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    gameSession = new GameSessionModel();
                    gameSession.setGameSessionId(rs.getLong("game_session_id"));
                    gameSession.setSessionStart(rs.getTimestamp("session_start").toLocalDateTime());
                    gameSession.setSessionEnd(rs.getTimestamp("session_end").toLocalDateTime());
                    gameSession.setScore(rs.getInt("score"));

                    GameDAO gameDAO = new GameDAO();
                    GameModel game = gameDAO.findById(rs.getLong("game_id"));
                    gameSession.setGame(game);


                    Set<PlayerModel> players = getPlayersForSession(gameSession);
                    gameSession.setPlayers(players);
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return gameSession;
    }

    private void addPlayersToSession(GameSessionModel gameSession) throws SQLException {
        String INSERT_PLAYER_SESSION_SQL = "INSERT INTO session_players (game_session_id, player_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(INSERT_PLAYER_SESSION_SQL)) {
            for (PlayerModel player : gameSession.getPlayers()) {
                stmt.setLong(1, gameSession.getGameSessionId());
                stmt.setLong(2, player.getPlayerId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    private Set<PlayerModel> getPlayersForSession(GameSessionModel gameSession) throws SQLException {
        String SELECT_PLAYERS_FOR_SESSION_SQL = "SELECT * FROM game_sessions_players WHERE game_sessions_id = ?";
        Set<PlayerModel> players = new HashSet<>();
        PlayerDAO playerDAO = new PlayerDAO();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PLAYERS_FOR_SESSION_SQL)) {
            stmt.setLong(1, gameSession.getGameSessionId());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PlayerModel player = playerDAO.findById(rs.getLong("players_id"));
                    players.add(player);
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return players;
    }

    private void removePlayersFromSession(GameSessionModel gameSession) throws SQLException {
        String DELETE_PLAYER_FROM_SESSION_SQL = "DELETE FROM session_players WHERE game_session_id = ? AND player_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE_PLAYER_FROM_SESSION_SQL)) {
            for (PlayerModel player : gameSession.getPlayers()) {
                stmt.setLong(1, gameSession.getGameSessionId());
                stmt.setLong(2, player.getPlayerId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public List<GameSessionModel> findAll() throws SQLException {
        String sql = "SELECT * FROM game_sessions";
        List<GameSessionModel> gameSessions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                GameSessionModel gameS = new GameSessionModel();
                gameS.setGameSessionId(rs.getLong("game_session_id"));
                gameS.setSessionStart(rs.getTimestamp("session_start").toLocalDateTime());
                gameS.setSessionEnd(rs.getTimestamp("session_end").toLocalDateTime());
                gameS.setScore(rs.getInt("score"));

                gameSessions.add(gameS);
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }

        return gameSessions;
    }

    public void update(GameSessionModel gameSession) throws SQLException {
        String sql = "UPDATE game_sessions SET game_id = ?, session_start = ?, session_end = ?, score = ? WHERE game_session_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, gameSession.getGame().getGameId());
            stmt.setTimestamp(2, Timestamp.valueOf(gameSession.getSessionStart()));
            stmt.setTimestamp(3, Timestamp.valueOf(gameSession.getSessionEnd()));
            stmt.setInt(4, gameSession.getScore());
            stmt.setLong(5, gameSession.getGameSessionId());

            stmt.executeUpdate();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM game_sessions WHERE game_session_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    private GameSessionModel mapResultSetToGameSession(ResultSet rs) throws SQLException {
        GameSessionModel gameSession = new GameSessionModel();
        GameDAO gameDAO = new GameDAO();

        gameSession.setGameSessionId(rs.getLong("game_session_id"));
        GameModel game = gameDAO.findById(rs.getLong("game_id"));
        gameSession.setGame(game);
        gameSession.setSessionStart(rs.getTimestamp("session_start").toLocalDateTime());
        gameSession.setSessionEnd(rs.getTimestamp("session_end").toLocalDateTime());
        gameSession.setScore(rs.getInt("score"));

        return gameSession;
    }


    public List<GameSessionModel> findByPlayerId(Long playerId) throws SQLException {
        String sql = "SELECT * FROM game_sessions gs JOIN game_session_players gsp ON gs.game_session_id = gsp.game_session_id WHERE gsp.player_id = ?";
        List<GameSessionModel> gameSessions = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, playerId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    gameSessions.add(mapResultSetToGameSession(rs));
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }

        return gameSessions;
    }
}
