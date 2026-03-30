package be.backend.controller;

import be.backend.dto.ClubDto;
import be.backend.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/clubs")
@RequiredArgsConstructor
public class ClubsController {
    private final ClubService clubService;

    @GetMapping("/all")
    public List<ClubDto> getAllClubs(){
        return clubService.findAll();
    }
}
