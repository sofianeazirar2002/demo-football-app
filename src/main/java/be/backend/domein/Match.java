package be.backend.domein;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "`match`")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Club homeTeam;
    @ManyToOne
    private Club awayTeam;
    private LocalDateTime kickOffTime;
    private Integer homeScore;
    private Integer awayScore;
    private MatchStatus status;
    private String aiPrediction;
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;
    @OneToMany
    private List<Prediction> predictions = new ArrayList<>();

}
