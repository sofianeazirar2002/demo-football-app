package be.backend.domein;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "pronostic_group_user")
public class PronosticGroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int totalScore;
    @OneToMany(mappedBy = "groupUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prediction> predictions = new ArrayList<>();
    @ManyToOne
    private User user;
    @ManyToOne
    private PronosticGroup pronosticGroup;
}
