package com.auth.loginauthentication.Service;


import com.auth.loginauthentication.DTO.AuthRequest;
import com.auth.loginauthentication.DTO.AuthResponse;
import com.auth.loginauthentication.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse signUp(AuthRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        String  encodedPassword = passwordEncoder.encode(request.getPassword());
        String accessToken = jwtService.generateAccessToken(request.getEmail());
        String refreshToken = jwtService.generateRefreshToken(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .refreshToken(refreshToken)
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
