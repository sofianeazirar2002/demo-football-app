package be.backend.controller;

import be.backend.config.JwtTokenUtil;
import be.backend.domein.User;
import be.backend.dto.LoginDto;
import be.backend.dto.UserDto;
import be.backend.service.JwtUserService;
import be.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUserService jwtUserService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto dto) {
        return jwtUserService.registerNew(dto.username(),dto.nickname(), dto.email(),dto.logoPath(), dto.password());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        String loginInput = userService.checkIfEmail(dto.username());
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginInput, dto.password());
        Authentication auth = authenticationManager.authenticate(authToken);

        String token = jwtTokenUtil.generateToken(auth);

        return ResponseEntity.ok(Map.of("token", token));
    }


    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return new UserDto(user.getId(),user.getUsername(),user.getNickname(),
                user.getEmail(),user.getLogoPath(), user.getPasswordHash(), user.getPoints());
    }

    @DeleteMapping("/me/delete")
    public void deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        userService.deleteById(user.getId());
    }

    @PutMapping
    public void updateUser(@RequestParam int id,@RequestParam String nickname,@RequestParam String logoPath) {
        userService.updateNameAndLogoPath(id,nickname, logoPath);
    }

    @GetMapping("/check")
    public boolean checkUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        return jwtUserService.checkUsernameAndPassword(username, password);
    }

    @GetMapping("/checkUsernameEmail")
    public boolean checkUsernameOrEmailTaken(@RequestParam String username, @RequestParam String email) {
        return userService.checkUsernameOrEmailTaken(username, email);
    }

    @PatchMapping("/forgetPassword")
    public boolean forgetPassword(@RequestParam String username,@RequestParam String email,
                               @RequestParam String newPassword) {
        return jwtUserService.forgetPassword(username,email, newPassword);
    }

    @PatchMapping("/addPoints")
    public void addPoints(@RequestParam int userId, @RequestParam int points) {
        userService.addPointsToUser(userId, points);
    }

}
