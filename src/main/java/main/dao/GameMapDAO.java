package main.dao;

import main.exception.ErrorHandler;
import main.model.GameMapModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameMapDAO {

    public void save(GameMapModel map) throws SQLException {
        String sql = "INSERT INTO game_maps (name, location, game_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, map.getName());
            stmt.setString(2, map.getLocation());
            stmt.setLong(3, map.getGame().getGameId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    map.setGameMapId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public GameMapModel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM game_maps WHERE game_map_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GameMapModel map = new GameMapModel();
                    map.setGameMapId(rs.getLong("game_map_id"));
                    map.setName(rs.getString("name"));
                    map.setLocation(rs.getString("location"));
                    return map;
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return null;
    }

    public List<GameMapModel> findAll() throws SQLException {
        String sql = "SELECT * FROM game_maps";
        List<GameMapModel> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GameMapModel map = new GameMapModel();
                map.setGameMapId(rs.getLong("game_map_id"));
                map.setName(rs.getString("name"));
                map.setLocation(rs.getString("location"));
                list.add(map);
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return list;
    }

    public void update(GameMapModel map) throws SQLException {
        String sql = "UPDATE game_maps SET name = ?, location = ? WHERE game_map_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, map.getName());
            stmt.setString(2, map.getLocation());
            stmt.setLong(3, map.getGameMapId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM game_maps WHERE game_map_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }
}
