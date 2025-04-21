package main.dao;

import main.exception.ErrorHandler;
import main.model.GameModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    public void save(GameModel game) throws SQLException {
        String sql = "INSERT INTO games (title, genre) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, game.getTitle());
            stmt.setString(2, game.getGenre());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    game.setGameId(generatedKeys.getLong(1)); // ��������� ID ������� � ������
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public GameModel findById(Long game_id) throws SQLException {
        String sql = "SELECT * FROM games WHERE game_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, game_id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    GameModel game = new GameModel();
                    game.setGameId(rs.getLong("game_id"));
                    game.setTitle(rs.getString("title"));
                    game.setGenre(rs.getString("genre"));
                    return game;
                }
            }catch (SQLException e) {
                ErrorHandler.handleSQLException(e);
            } catch (Exception e) {
                ErrorHandler.handleUnexpectedException(e);
            }
        }
        return null;
    }

    public List<GameModel> findAll() throws SQLException {
        String sql = "SELECT * FROM games";
        List<GameModel> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GameModel game = new GameModel();
                game.setGameId(rs.getLong("game_id"));
                game.setTitle(rs.getString("title"));
                game.setGenre(rs.getString("genre"));
                list.add(game);
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return list;
    }

    public void update(GameModel game) throws SQLException {
        if (game.getGameId() == null) {
            throw new SQLException("Game ID cannot be null. Ensure the game has a valid ID before updating.");
        }

        String sql = "UPDATE games SET title = ?, genre = ? WHERE game_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Updating Game ID: " + game.getGameId());
            System.out.println("Title: " + game.getTitle());
            System.out.println("Genre: " + game.getGenre());

            stmt.setString(1, game.getTitle());
            stmt.setString(2, game.getGenre());
            stmt.setLong(3, game.getGameId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating game with ID: " + game.getGameId());
            ErrorHandler.handleSQLException(e);
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error while updating game.");
            ErrorHandler.handleUnexpectedException(e);
            throw e;
        }
    }


    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM games WHERE game_id = ?";
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
