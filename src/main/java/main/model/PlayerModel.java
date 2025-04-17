package main.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class PlayerModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;

    private String username;
    private String email;
    private String password_hash;
    private LocalDateTime created_at;
    private String rank;

    @OneToMany(mappedBy = "player")
    private Set<MatchStatModel> matchStats;

    @ManyToMany
    @JoinTable(
            name = "player_team",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<TeamModel> teams;
}
