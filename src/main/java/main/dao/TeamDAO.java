package main.dao;

import main.exception.ErrorHandler;
import main.model.PlayerModel;
import main.model.TeamModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {

    public void save(TeamModel team) throws SQLException {
        String sql = "INSERT INTO teams (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    team.setTeamId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public TeamModel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM teams WHERE team_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TeamModel team = new TeamModel();
                    team.setTeamId(rs.getLong("team_id"));
                    team.setName(rs.getString("name"));
                    team.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                    return team;
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return null;
    }

    public List<TeamModel> findAll() throws SQLException {
        String sql = "SELECT * FROM teams";
        List<TeamModel> teams = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                TeamModel team = new TeamModel(); // ���������� @NoArgsConstructor
                team.setTeamId(rs.getLong("team_id")); // ��� rs.getLong("id") � ������, ��� � ����
                team.setName(rs.getString("name"));
                team.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                teams.add(team);
            }
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return teams;
    }

    public void update(TeamModel team) throws SQLException {
        String sql = "UPDATE teams SET name = ? WHERE team_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, team.getName());
            stmt.setLong(2, team.getTeamId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM teams WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }
}