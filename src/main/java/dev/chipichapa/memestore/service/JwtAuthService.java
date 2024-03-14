package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.dto.auth.JwtResponse;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.exception.BadRequestException;
import dev.chipichapa.memestore.security.jwt.JwtTokenProvider;
import dev.chipichapa.memestore.security.tg.TgEntity;
import dev.chipichapa.memestore.security.tg.TgEntityFactory;
import dev.chipichapa.memestore.service.ifc.AuthService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {

    private final UserService userService;
    private final UserRegisterUseCase userRegister;

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        Long tgId = loginRequest.tgId();

        if (!userService.existsByTelegramId(tgId)) {
            checkRegisterFieldsOrThrow(loginRequest);
            userRegistration(loginRequest);
        }

        User user = userService.getByTgId(tgId);
        TgEntity tgEntity = TgEntityFactory.create(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(tgEntity,
                null,
                tgEntity.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwtResponse.setId(user.getId());
        jwtResponse.setTgId(user.getTgId());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getTgId(), user.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getTgId())
        );
        return jwtResponse;
    }

    private void userRegistration(JwtRequest loginRequest) {
        userRegister.register(new RegisterRequest(
                loginRequest.tgId(),
                loginRequest.username(),
                loginRequest.firstName(),
                loginRequest.lastName()));
    }

    private void checkRegisterFieldsOrThrow(JwtRequest loginRequest) {
        if (!(loginRequest.firstName() != null & loginRequest.lastName() != null & loginRequest.username() != null)){
            throw new BadRequestException("This user is not found and for registration you should return all payload fields");
        }
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
