package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.user.User;
import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.dto.auth.JwtResponse;
import dev.chipichapa.memestore.security.jwt.JwtTokenProvider;
import dev.chipichapa.memestore.service.ifc.AuthService;
import dev.chipichapa.memestore.service.ifc.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        User user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getUsername(), user.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getUsername())
        );
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
