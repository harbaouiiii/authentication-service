package com.example.authservice.services;

import com.example.authservice.config.JwtService;
import com.example.authservice.entities.Role;
import com.example.authservice.entities.User;
import com.example.authservice.exceptions.EmailExistsException;
import com.example.authservice.exceptions.UsernameExistsException;
import com.example.authservice.payload.request.LoginRequest;
import com.example.authservice.payload.request.RegisterRequest;
import com.example.authservice.payload.response.AuthenticationResponse;
import com.example.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if(Boolean.TRUE.equals(userRepository.existsByUserName(registerRequest.getUserName()))){
            throw new UsernameExistsException();
        }
        if(Boolean.TRUE.equals(userRepository.existsByEmail(registerRequest.getEmail()))){
            throw new EmailExistsException();
        }
        var user = User.builder()
                .userName(registerRequest.getUserName())
                .firstName(registerRequest.getFirstName().toUpperCase().charAt(0) + registerRequest.getFirstName().substring(1).toLowerCase())
                .lastName(registerRequest.getLastName().toUpperCase())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .dateOfBirth(registerRequest.getDateOfBirth())
                .phoneNumber(registerRequest.getPhoneNumber())
                .address(registerRequest.getAddress())
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword()
                )
        );
        var user = userRepository.findByUserName(loginRequest.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.
                builder()
                .token(jwtToken)
                .build();
    }
}
