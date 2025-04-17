package main.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;    //PK

    private String name;
    private LocalDateTime created_at;

    @ManyToMany(mappedBy = "teams")
    private Set<PlayerModel> members;

    @OneToMany(mappedBy = "teamA")
    private Set<MatchModel> matchesAsTeamA;

    @OneToMany(mappedBy = "teamB")
    private Set<MatchModel> matchesAsTeamB;
}
