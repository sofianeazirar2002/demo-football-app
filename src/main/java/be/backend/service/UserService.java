package be.backend.service;

import be.backend.domein.PronosticGroup;
import be.backend.domein.PronosticGroupUser;
import be.backend.domein.User;
import be.backend.repository.PronosticGroupRepository;
import be.backend.repository.PronosticGroupUserRepository;
import be.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PronosticGroupRepository pronosticGroupRepository;
    private final PronosticGroupUserRepository pronosticGroupUserRepository;

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String checkIfEmail(String username) {
        String loginInput = username;
        if (loginInput.contains("@")) {
            User user = findByEmail(loginInput);
            if (user == null) {
                return null;
            }
            return user.getUsername();
        }
        return username;
    }

    @Transactional
    public void deleteById(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<PronosticGroup> ownedGroups = pronosticGroupRepository.findByOwnerId(id);
        for (PronosticGroup group : ownedGroups) {
            Optional<PronosticGroupUser> nextOwnerMembership =
                    pronosticGroupUserRepository.findFirstByPronosticGroupIdAndUserIdNotOrderByIdAsc(group.getId(), id);

            if (nextOwnerMembership.isPresent()) {
                User newOwner = nextOwnerMembership.get().getUser();
                group.setOwner(newOwner);
                pronosticGroupRepository.save(group);
            } else {
                pronosticGroupUserRepository.deleteByPronosticGroupId(group.getId());
                pronosticGroupRepository.delete(group);
            }
        }

        pronosticGroupUserRepository.deleteAllByUserId(id);

        userRepository.delete(user);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void updateNameAndLogoPath(int id,String nickname, String logoPath) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setNickname(nickname);
        user.setLogoPath(logoPath);
        userRepository.save(user);
    }

    public boolean checkUsernameOrEmailTaken(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }

    @Transactional
    public void addPointsToUser(int userId, int points) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setPoints(user.getPoints() + points);
        userRepository.save(user);
    }
}
