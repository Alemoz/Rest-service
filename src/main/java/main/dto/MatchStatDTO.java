package main.dto;

import lombok.Data;

@Data
public class MatchStatDTO {
    private Long id;
    private int kills;
    private int deaths;
    private int assists;
    private int score;
    private Long playerId;
    private Long matchId;
}
