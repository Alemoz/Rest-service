-- ===== GameModel =====
INSERT INTO games (game_id, title, genre) VALUES (1, 'Warzone', 'Shooter');
INSERT INTO games (game_id, title, genre) VALUES (2, 'Fantasy Quest', 'RPG');

-- ===== PlayerModel =====
INSERT INTO players (player_id, username, email, password_hash, created_at, rank) VALUES
(1, 'playerOne', 'one@mail.com', 'hashed_pw1', CURRENT_TIMESTAMP, 'Bronze'),
(2, 'playerTwo', 'two@mail.com', 'hashed_pw2', CURRENT_TIMESTAMP, 'Silver');

-- ===== TeamModel =====
INSERT INTO teams (team_id, name, created_at) VALUES
(1, 'Team Alpha', CURRENT_TIMESTAMP),
(2, 'Team Bravo', CURRENT_TIMESTAMP);

-- ===== GameMapModel =====
INSERT INTO game_maps (game_map_id, name, location, game_id) VALUES
(1, 'Dust Arena', 'Desert Zone', 1),
(2, 'Ice Fortress', 'Frozen North', 2);

-- ===== MatchModel =====
INSERT INTO matches (match_id, game_id, team_a_id, team_b_id, winner_id, game_map_id, match_date) VALUES
(1, 1, 1, 2, 1, 1, '2025-01-01'),
(2, 2, 2, 1, 2, 2, '2025-01-02');

-- ===== MatchStatModel =====
INSERT INTO match_stats (match_stat_id, kills, deaths, assists, score, player_id, match_id) VALUES
(1, 10, 2, 5, 1500, 1, 1),
(2, 8, 4, 7, 1400, 2, 1);

-- ===== GameSessionModel =====
INSERT INTO game_sessions (game_session_id, session_start, session_end, score, game_id) VALUES
(1, '2025-04-01 10:00:00', '2025-04-01 11:00:00', 1200, 1),
(2, '2025-04-02 12:00:00', '2025-04-02 13:00:00', 1400, 2);

-- ===== Join Tables (ManyToMany) =====
-- Player_Team (for @ManyToMany between PlayerModel and TeamModel)
INSERT INTO players_teams (players_id, teams_id) VALUES (1, 1), (2, 2);

-- GameSession_Players (assuming a join table exists for GameSession.players)
INSERT INTO game_sessions_players (game_sessions_id, players_id) VALUES
(1, 1),
(1, 2),
(2, 1);