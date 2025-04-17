package main.dao;

import main.exception.ErrorHandler;
import main.model.GameMapModel;
import main.model.GameModel;
import main.model.MatchModel;
import main.model.TeamModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDAO {

    public void save(MatchModel match) throws SQLException {
        String sql = "INSERT INTO matches (game_id, team_a_id, team_b_id, match_date, winner_id, game_map_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, match.getGameModel().getGameId());
            stmt.setLong(2, match.getTeamA().getTeamId());
            stmt.setLong(3, match.getTeamB().getTeamId());
            stmt.setTimestamp(4, Timestamp.valueOf(match.getMatchDate()));
            stmt.setLong(5, match.getWinner() != null ? match.getWinner().getTeamId() : null);
            stmt.setLong(6, match.getMap().getGameMapId());

            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    match.setMatchId(generatedKeys.getLong(1));
                }
            }
        }
    }

    public MatchModel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM matches WHERE match_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MatchModel match = new MatchModel();
                    match.setMatchId(rs.getLong("match_id"));
                    match.setMatchDate(rs.getTimestamp("match_date").toLocalDateTime());


                    GameModel gameModel = new GameModel();
                    gameModel.setGameId(rs.getLong("game_id"));
                    match.setGameModel(gameModel);

                    TeamModel teamA = new TeamModel();
                    teamA.setTeamId(rs.getLong("team_a_id"));
                    match.setTeamA(teamA);

                    TeamModel teamB = new TeamModel();
                    teamB.setTeamId(rs.getLong("team_b_id"));
                    match.setTeamB(teamB);

                    TeamModel winner = new TeamModel();
                    winner.setTeamId(rs.getLong("winner_id"));
                    match.setWinner(winner);

                    GameMapModel map = new GameMapModel();
                    map.setGameMapId(rs.getLong("game_map_id"));
                    match.setMap(map);

                    return match;
                }
            }catch (SQLException e) {
                ErrorHandler.handleSQLException(e);
            } catch (Exception e) {
                ErrorHandler.handleUnexpectedException(e);
            }
        }
        return null;
    }

    public List<MatchModel> findAll() throws SQLException {
        List<MatchModel> matches = new ArrayList<>();
        String sql = "SELECT * FROM matches";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MatchModel match = new MatchModel();
                match.setMatchId(rs.getLong("match_id"));


                GameModel gameModel = new GameModel();
                gameModel.setGameId(rs.getLong("game_id"));
                match.setGameModel(gameModel);

                TeamModel teamA = new TeamModel();
                teamA.setTeamId(rs.getLong("team_a_id"));
                match.setTeamA(teamA);

                TeamModel teamB = new TeamModel();
                teamB.setTeamId(rs.getLong("team_b_id"));
                match.setTeamB(teamB);

                TeamModel winner = new TeamModel();
                winner.setTeamId(rs.getLong("winner_id"));
                match.setWinner(winner);

                GameMapModel map = new GameMapModel();
                map.setGameMapId(rs.getLong("game_map_id"));
                match.setMap(map);

                match.setMatchDate(rs.getTimestamp("match_date").toLocalDateTime());


                matches.add(match);
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return matches;
    }

    public void update(MatchModel match) throws SQLException {
        String sql = "UPDATE matches SET game_id = ?, team_a_id = ?, team_b_id = ?, match_date = ?, winner_id = ?, game_map_id = ? WHERE match_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, match.getGameModel().getGameId());
            stmt.setLong(2, match.getTeamA().getTeamId());
            stmt.setLong(3, match.getTeamB().getTeamId());
            stmt.setTimestamp(4, Timestamp.valueOf(match.getMatchDate()));
            stmt.setLong(5, match.getWinner() != null ? match.getWinner().getTeamId() : null);
            stmt.setLong(6, match.getMap().getGameMapId());
            stmt.setLong(7, match.getMatchId());

            stmt.executeUpdate();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM matches WHERE match_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }
}
