package main.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GameMapModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameMapId;

    private String name;
    private String location;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameModel game;
}
