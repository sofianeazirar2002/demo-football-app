package be.backend.repository;

import be.backend.domein.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {
    @Query("SELECT c FROM Club c LEFT JOIN FETCH c.competition")
    List<Club> findAllWithCompetition();
}
