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
@Table(name = "pronostic_group")
public class PronosticGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String joinCode;
    private LocalDateTime created;
    private boolean isPublic;
    @OneToMany(mappedBy = "pronosticGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PronosticGroupUser> members = new ArrayList<>();

    @ManyToOne
    private Competition competition;
    @ManyToOne
    private User owner;

}
