package main.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MatchDTO {
    private Long id;
    private Long gameId;
    private Long teamAId;
    private Long teamBId;
    private Long winnerId;
    private Long mapId;
    private LocalDateTime matchDate;
}
