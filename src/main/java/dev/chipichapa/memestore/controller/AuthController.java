package dev.chipichapa.memestore.controller;

import dev.chipichapa.memestore.domain.dto.BasicApiResponse;
import dev.chipichapa.memestore.dto.auth.JwtRefreshRequest;
import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.dto.auth.JwtResponse;
import dev.chipichapa.memestore.service.ifc.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BasicApiResponse<?>> login(@RequestBody JwtRequest loginRequest) {
        return new ResponseEntity<>(
                new BasicApiResponse<>(false, authService.login(loginRequest)),
                HttpStatus.OK
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<BasicApiResponse<JwtResponse>> refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        return new ResponseEntity<>(
                new BasicApiResponse<>(false, authService.refresh(refreshRequest.getRefreshToken())),
                HttpStatus.OK
        );
    }
}
