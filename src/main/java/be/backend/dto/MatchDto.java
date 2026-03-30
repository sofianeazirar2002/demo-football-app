package be.backend.dto;


import be.backend.domein.Competition;
import be.backend.domein.MatchStatus;

import java.time.LocalDateTime;


public record MatchDto(int id, ClubDto homeTeam, ClubDto awayTeam, LocalDateTime kickOffTime, Integer homeScore,
                       Integer awayScore, MatchStatus status, String competitionLogoPng,String competitionName,
                       String aiPrediction) {
}
