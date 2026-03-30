package be.backend.service;

import be.backend.domein.Club;
import be.backend.dto.ClubDto;
import be.backend.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    public List<ClubDto> findAll() {
        List<Club> clubs = clubRepository.findAllWithCompetition();
        return clubs.stream()
                .map(c -> new ClubDto(
                        c.getId(),
                        c.getName(),
                        c.getLogoUrl(),
                        c.getCompetition().getName()
                ))
                .toList();
    }
}
