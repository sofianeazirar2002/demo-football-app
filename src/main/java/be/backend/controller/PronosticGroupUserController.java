package be.backend.controller;

import be.backend.dto.PronosticGroupUserDto;
import be.backend.service.PronosticGroupUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/pronostic-group-users")
@RequiredArgsConstructor
public class PronosticGroupUserController {
    private final PronosticGroupUserService pronosticGroupUserService;

    @GetMapping
    public List<PronosticGroupUserDto> getPronosticGroupUsers(@RequestParam int groupId) {
        return pronosticGroupUserService.getUsersByGroupId(groupId);
    }

    @GetMapping("/byUserAndGroup")
    public PronosticGroupUserDto getPronosticGroupUserByUserAndGroup(@RequestParam int userId,
                                                                     @RequestParam int groupId) {
            return pronosticGroupUserService.getGroupUserByUserIdAndGroupId(userId, groupId);
    }

    @GetMapping("/groupLimit")
    public boolean hasReachedGroupLimit(@RequestParam int userId) {
        return pronosticGroupUserService.hasUserReachedGroupLimit(userId);
    }
}
