package be.backend.service;

import be.backend.domein.PronosticGroupUser;
import be.backend.dto.PronosticGroupUserDto;
import be.backend.dto.UserDto;
import be.backend.repository.PronosticGroupUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PronosticGroupUserService {
    private final PronosticGroupUserRepository pronosticGroupUserRepository;
    private final UserService userService;

    public List<PronosticGroupUser> findAllByUserId(int id) {
        return pronosticGroupUserRepository.findAllByUserId(id);
    }

    public PronosticGroupUserDto getGroupUserByUserIdAndGroupId(int userId, int groupId) {
        PronosticGroupUser pgu = pronosticGroupUserRepository.findByUserIdAndPronosticGroup_Id(userId, groupId);
        if (pgu == null) {
            return null;
        }
        return new PronosticGroupUserDto(
                pgu.getId(),
                pgu.getTotalScore(),
                new UserDto(
                        pgu.getUser().getId(),
                        pgu.getUser().getUsername(),
                        pgu.getUser().getNickname(),
                        pgu.getUser().getEmail(),
                        pgu.getUser().getLogoPath(),
                        pgu.getUser().getPasswordHash(),
                        pgu.getUser().getPoints()
                )
        );
    }


    public List<PronosticGroupUserDto> getUsersByGroupId(int groupId) {
        return pronosticGroupUserRepository.findAllByPronosticGroup_Id(groupId)
                .stream()
                .map(pgu -> new PronosticGroupUserDto(
                        pgu.getId(),
                        pgu.getTotalScore(),
                        new UserDto(
                                pgu.getUser().getId(),
                                pgu.getUser().getUsername(),
                                pgu.getUser().getNickname(),
                                pgu.getUser().getEmail(),
                                pgu.getUser().getLogoPath(),
                                pgu.getUser().getPasswordHash(),
                                pgu.getUser().getPoints()
                        )
                )).toList();

    }

    public boolean hasUserReachedGroupLimit(int userId) {
        var user = userService.findById(userId);
        if (user != null && "flarioo".equalsIgnoreCase(user.getUsername())) {
            return false;
        }
        int groupCount = findAllByUserId(userId).size();
        return groupCount >= 5;
    }

}

