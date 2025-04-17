package main.dto;

import lombok.Data;

@Data
public class GameMapDTO {
    private Long id;
    private String name;
    private String location;
    private Long gameId;
}
