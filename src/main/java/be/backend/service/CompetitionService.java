package be.backend.service;

import be.backend.domein.Competition;
import be.backend.dto.ClubDto;
import be.backend.dto.CompetitionDto;
import be.backend.dto.MatchDto;
import be.backend.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CompetitionService {
    private final CompetitionRepository competitionRepository;

    public List<CompetitionDto> findAll() {
        List<Competition> competitions = competitionRepository.findAllWithMatches();
        return competitions.stream()
                .map(c -> new CompetitionDto(
                        c.getId(),
                        c.getName(),
                        c.getLogoPng(),
                        c.isActive(),
                        c.getMatches().stream()
                                .map(m -> new MatchDto(
                                        m.getId(),
                                        new ClubDto(m.getHomeTeam().getId(),
                                                m.getHomeTeam().getName(),
                                                m.getHomeTeam().getLogoUrl(),
                                                m.getHomeTeam().getCompetition().getName()
                                        ),
                                        new ClubDto(m.getAwayTeam().getId(),
                                                m.getAwayTeam().getName(),
                                                m.getAwayTeam().getLogoUrl(),
                                                m.getAwayTeam().getCompetition().getName()),
                                        m.getKickOffTime(),
                                        m.getHomeScore(),
                                        m.getAwayScore(),m.getStatus(),m.getCompetition().getLogoPng(),m.getCompetition().getName(),
                                        m.getAiPrediction()))
                                .toList()
                ))
                .toList();
    }

}
