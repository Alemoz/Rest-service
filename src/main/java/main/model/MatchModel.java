package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameModel gameModel;

    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private TeamModel teamA;

    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private TeamModel teamB;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private TeamModel winner;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private GameMapModel map;

    private LocalDateTime matchDate;
}
