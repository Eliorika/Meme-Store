package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.dto.auth.JwtRefreshRequest;
import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.dto.auth.JwtResponse;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.service.ifc.AuthService;
import dev.chipichapa.memestore.usecase.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRegisterUseCase userRegisterUseCase;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest.getRefreshToken());
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registrationRequest){
        userRegisterUseCase.register(registrationRequest);
    }
}
