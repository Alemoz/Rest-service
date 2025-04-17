package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class GameSessionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameSessionId;//PK

    @ManyToMany
    @JoinTable(
            name = "game_sessions_players",
            joinColumns = @JoinColumn(name = "game_sessions_id"),
            inverseJoinColumns = @JoinColumn(name = "players_id")
    )
    private Set<PlayerModel> players;

    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private GameModel game;

    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;
    private int score;

}
