package com.example.authservice.controllers;

import com.example.authservice.exceptions.EmailExistsException;
import com.example.authservice.exceptions.UsernameExistsException;
import com.example.authservice.payload.request.LoginRequest;
import com.example.authservice.payload.request.RegisterRequest;
import com.example.authservice.payload.response.AuthenticationResponse;
import com.example.authservice.services.AuthenticationService;
import com.example.authservice.utils.Urls;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Urls.BASE_URL)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserDetailsService userService;

    @PostMapping(Urls.REGISTER_URL)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        try{
            return ResponseEntity.ok(authenticationService.register(registerRequest));
        } catch (UsernameExistsException | EmailExistsException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(Urls.LOGIN_URL)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
