package be.backend.domein;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "prediction")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "pronistic_group_user_id")
    private PronosticGroupUser groupUser;
    @ManyToOne
    private Match match;
    private int predictedHomeScore;
    private int predictedAwayScore;
    private int pointsAwarded;

}
