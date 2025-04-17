package main.dao;

import main.exception.ErrorHandler;
import main.model.MatchStatModel;
import main.service.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchStatDAO {
    public void save(MatchStatModel stat) throws SQLException {
        String sql = "INSERT INTO match_stats (kills, deaths, assists, score, player_id, match_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stat.getKills());
            stmt.setInt(2, stat.getDeaths());
            stmt.setInt(3, stat.getAssists());
            stmt.setInt(4, stat.getScore());
            stmt.setLong(5, stat.getPlayer().getPlayerId());
            stmt.setLong(6, stat.getMatch().getMatchId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    stat.setMatchStatId(generatedKeys.getLong(1));
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public MatchStatModel findById(Long id) throws SQLException {
        String sql = "SELECT * FROM match_stats WHERE match_stat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MatchStatModel stat = new MatchStatModel();
                    stat.setMatchStatId(rs.getLong("match_stat_id"));
                    stat.setKills(rs.getInt("kills"));
                    stat.setDeaths(rs.getInt("deaths"));
                    stat.setAssists(rs.getInt("assists"));
                    stat.setScore(rs.getInt("score"));
                    return stat;
                }
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return null;
    }

    public List<MatchStatModel> findAll() throws SQLException {
        List<MatchStatModel> stats = new ArrayList<>();
        String sql = "SELECT * FROM match_stats";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MatchStatModel stat = new MatchStatModel();
                stat.setMatchStatId(rs.getLong("match_stat_id"));
                stat.setKills(rs.getInt("kills"));
                stat.setDeaths(rs.getInt("deaths"));
                stat.setAssists(rs.getInt("assists"));
                stat.setScore(rs.getInt("score"));
                stats.add(stat);
            }
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
        return stats;
    }

    public void update(MatchStatModel stat) throws SQLException {
        String sql = "UPDATE match_stats SET kills = ?, deaths = ?, assists = ?, score = ?, player_id = ?, match_id = ? WHERE match_stat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stat.getKills());
            stmt.setInt(2, stat.getDeaths());
            stmt.setInt(3, stat.getAssists());
            stmt.setInt(4, stat.getScore());
            stmt.setLong(5, stat.getPlayer().getPlayerId());
            stmt.setLong(6, stat.getMatch().getMatchId());
            stmt.setLong(7, stat.getMatchStatId());
            stmt.executeUpdate();
        }catch (SQLException e) {
            ErrorHandler.handleSQLException(e);
        } catch (Exception e) {
            ErrorHandler.handleUnexpectedException(e);
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM match_stats WHERE match_stat_id = ?";
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
