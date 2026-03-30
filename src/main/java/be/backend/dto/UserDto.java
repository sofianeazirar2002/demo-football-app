package be.backend.dto;


    public record UserDto(int id,String username,String nickname,String email,String logoPath, String password,int points) {
}
