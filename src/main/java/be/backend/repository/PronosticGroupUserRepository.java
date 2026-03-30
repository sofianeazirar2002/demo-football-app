package be.backend.repository;

import be.backend.domein.PronosticGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PronosticGroupUserRepository extends JpaRepository<PronosticGroupUser, Integer> {
    List<PronosticGroupUser> findAllByUserId(int userId);
    List<PronosticGroupUser> findAllByPronosticGroup_Id(int groupId);
    PronosticGroupUser findByUserIdAndPronosticGroup_Id(int userId, int groupId);

    void deleteByPronosticGroupId(int pronosticGroupId);

    void deleteAllByUserId(int userId);

    Optional<PronosticGroupUser> findFirstByPronosticGroupIdAndUserIdNotOrderByIdAsc(int pronosticGroupId, int excludedUserId);


}
