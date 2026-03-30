package be.backend.controller;

import be.backend.dto.CompetitionDto;
import be.backend.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/competitions")
@RequiredArgsConstructor
public class CompetitionsController {
    private final CompetitionService competitionService;

    @GetMapping
    public List<CompetitionDto> findAll() {
        return competitionService.findAll();
    }
}
