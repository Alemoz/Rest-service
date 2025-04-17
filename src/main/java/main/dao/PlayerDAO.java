package main.dao;

import main.exception.ErrorHandler;
import main.model.PlayerModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    public void save(PlayerModel player) throws SQLException {
        String sql = "INSERT INTO players (username, email, rank) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, player.getUsername());
            stmt.setString(2, player.getEmail());
            stmt.setString(3, player.getRank());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    player.setPlayerId(generatedKeys.getLong(1));
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }


    public List<PlayerModel> findAll() throws SQLException {
        List<PlayerModel> players = new ArrayList<>();
        String sql = "SELECT * FROM players";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PlayerModel player = new PlayerModel();
                player.setPlayerId(rs.getLong("player_id"));
                player.setUsername(rs.getString("username"));
                player.setEmail(rs.getString("email"));
                player.setRank(rs.getString("rank"));
                players.add(player);
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }

        return players;
    }

    public PlayerModel findById(long id) throws SQLException {
        String sql = "SELECT * FROM players WHERE player_id = ?";
        PlayerModel player = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    player = new PlayerModel();
                    player.setPlayerId(rs.getLong("player_id"));
                    player.setUsername(rs.getString("username"));
                    player.setEmail(rs.getString("email"));
                    player.setRank(rs.getString("rank"));
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }

        return player;
    }

    public void update(PlayerModel player) throws SQLException {
        String sql = "UPDATE players SET username = ?, email = ?, rank = ? WHERE player_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getUsername());
            stmt.setString(2, player.getEmail());
            stmt.setString(3, player.getRank());
            stmt.setLong(4, player.getPlayerId());
            stmt.executeUpdate();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM players WHERE player_id = ?";

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
}
