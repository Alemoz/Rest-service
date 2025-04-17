package main.dto;

import lombok.Data;

@Data
public class PlayerDTO {
    private Long id;
    private String username;
    private String email;
    private String rank;
}
