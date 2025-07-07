package com.harsh.rms.security.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.harsh.rms.dto.AuthRequestDto;
import com.harsh.rms.dto.AuthResponseDto;
import com.harsh.rms.models.User;
import com.harsh.rms.repositories.UserRepository;
import com.harsh.rms.security.jwt.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signup(@RequestBody AuthRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body("Email already in use.");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setName(request.name());
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        User user = userRepository.findByEmail(request.email());
        if (user == null || !passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
