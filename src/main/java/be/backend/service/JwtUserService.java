package be.backend.service;

import be.backend.domein.User;
import be.backend.dto.UserDto;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserService implements UserDetailsService {
    private final UserService userService;
    private final PasswordEncoder pwEncoder;

    public JwtUserService(UserService userService) {
        this.userService = userService;
        this.pwEncoder = new BCryptPasswordEncoder();
    }

    public Authentication getAuthentication(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("flarioo".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                authorities
        );
    }


    public UserDto registerNew(String username,String nickname, String email,String logoPath, String password) {
        if (userService.existsByUsername(username)) {
            throw new IllegalArgumentException("Username taken");
        }
        User u = new User();
        u.setUsername(username);
        u.setNickname(nickname);
        u.setEmail(email);
        u.setLogoPath(logoPath);
        u.setPasswordHash(pwEncoder.encode(password));
        u.setPoints(0);
        User savedUser = userService.save(u);
        return new UserDto(savedUser.getId(),savedUser.getUsername(),savedUser.getNickname(),
                savedUser.getEmail(),savedUser.getLogoPath(), savedUser.getEmail(), savedUser.getPoints());
    }

    public boolean checkUsernameAndPassword(String usernameOrEmail, String password) {
        User user;
        if (usernameOrEmail.contains("@")) {
            user = userService.findByEmail(usernameOrEmail);
        } else {
            user = userService.findByUsername(usernameOrEmail);
        }
        if (user == null) {
            return false;
        }
        return pwEncoder.matches(password, user.getPasswordHash());
    }

    @Transactional
    public boolean forgetPassword(String username, String email, String newPassword) {
        User user = userService.findByUsername(username);
        if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
            return false;
        }
        user.setPasswordHash(pwEncoder.encode(newPassword));
        userService.save(user);
        return true;
    }


}
