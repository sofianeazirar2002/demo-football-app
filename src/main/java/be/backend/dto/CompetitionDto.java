package be.backend.dto;


import java.util.List;

public record CompetitionDto(int id, String name, String logoPng,boolean isActive,List<MatchDto> matches) {


}
