package main.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
@Data
public class GameSessionDTO {
    private Long id;
    private ArrayList<Long> playerIds;
    private Long gameId;
    private LocalDateTime sessionStart;
    private LocalDateTime sessionEnd;
    private int score;
}
