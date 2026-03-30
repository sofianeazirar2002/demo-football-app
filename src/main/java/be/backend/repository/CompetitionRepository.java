package be.backend.repository;

import be.backend.domein.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
    @Query("SELECT distinct c FROM Competition c LEFT JOIN FETCH c.matches")
    List<Competition> findAllWithMatches();
}
