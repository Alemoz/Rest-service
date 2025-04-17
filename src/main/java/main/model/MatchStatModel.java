package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchStatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchStatId;        //PK

    private int kills;
    private int deaths;
    private int assists;
    private int score;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerModel player;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private MatchModel match;
}
