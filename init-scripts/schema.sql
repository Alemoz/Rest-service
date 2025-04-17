-- Создание таблицы игр
CREATE TABLE games (
    game_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL
);

-- Создание таблицы игровых сессий
CREATE TABLE game_sessions (
    game_session_id BIGSERIAL PRIMARY KEY,
    session_start TIMESTAMP NOT NULL,
    session_end TIMESTAMP NOT NULL,
    score INT NOT NULL,
    game_id BIGINT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE
);

-- Создание таблицы игроков
CREATE TABLE players (
    player_id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rank VARCHAR(50),
    team_id BIGINT,
    game_session_id BIGINT,
    FOREIGN KEY (team_id) REFERENCES teams(team_id) ON DELETE SET NULL,
    FOREIGN KEY (game_session_id) REFERENCES game_sessions(game_session_id) ON DELETE CASCADE
);

-- Создание таблицы команд
CREATE TABLE teams (
    team_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    game_id BIGINT,
    game_session_id BIGINT,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (game_session_id) REFERENCES game_sessions(game_session_id) ON DELETE CASCADE
);

-- Создание таблицы карт
CREATE TABLE game_maps (
    game_map_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    game_id BIGINT NOT NULL,
    game_session_id BIGINT,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (game_session_id) REFERENCES game_sessions(game_session_id) ON DELETE CASCADE
);

-- Создание таблицы матчей
CREATE TABLE matches (
    match_id BIGSERIAL PRIMARY KEY,
    game_id BIGINT NOT NULL,
    team_a_id BIGINT NOT NULL,
    team_b_id BIGINT NOT NULL,
    winner_id BIGINT,
    game_map_id BIGINT,
    match_date TIMESTAMP NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(game_id) ON DELETE CASCADE,
    FOREIGN KEY (team_a_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (team_b_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (winner_id) REFERENCES teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (game_map_id) REFERENCES game_maps(game_map_id) ON DELETE CASCADE
);

-- Создание таблицы статистики матчей
CREATE TABLE match_stats (
    match_stat_id BIGSERIAL PRIMARY KEY,
    kills INT NOT NULL,
    deaths INT NOT NULL,
    assists INT NOT NULL,
    score INT NOT NULL,
    player_id BIGINT NOT NULL,
    match_id BIGINT NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players(player_id) ON DELETE CASCADE,
    FOREIGN KEY (match_id) REFERENCES matches(match_id) ON DELETE CASCADE
);

CREATE TABLE players_teams (
    players_id BIGINT NOT NULL,
    teams_id BIGINT NOT NULL,
    PRIMARY KEY (players_id, teams_id),
    FOREIGN KEY (players_id) REFERENCES players(player_id),
    FOREIGN KEY (teams_id) REFERENCES teams(team_id)
);

CREATE TABLE game_sessions_players (
    game_sessions_id BIGINT NOT NULL,
    players_id BIGINT NOT NULL,
    PRIMARY KEY (game_sessions_id, players_id),
    FOREIGN KEY (game_sessions_id) REFERENCES game_sessions(game_session_id),
    FOREIGN KEY (players_id) REFERENCES players(player_id)
);