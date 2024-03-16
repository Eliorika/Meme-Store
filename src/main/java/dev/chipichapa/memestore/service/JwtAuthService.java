package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.auth.JwtRequest;
import dev.chipichapa.memestore.dto.auth.JwtResponse;
import dev.chipichapa.memestore.dto.auth.RegisterRequest;
import dev.chipichapa.memestore.security.jwt.JwtTokenProvider;
import dev.chipichapa.memestore.security.tg.TgEntity;
import dev.chipichapa.memestore.security.tg.TgEntityFactory;
import dev.chipichapa.memestore.service.ifc.AuthService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.UserRegisterUseCase;
import dev.chipichapa.memestore.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtAuthService implements AuthService {

    private final UserService userService;
    private final UserRegisterUseCase userRegister;

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtResponse auth(JwtRequest authRequest, Long telegramId) {
        JwtResponse jwtResponse = new JwtResponse();

        if (!userService.existsByTelegramId(telegramId)) {
            userRegistration(authRequest, telegramId);
        }
        User user = userService.getByTgId(telegramId);
        checkUsersFieldsAndUpdateIfRequired(user, authRequest);

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

    private void checkUsersFieldsAndUpdateIfRequired(User user, JwtRequest request) {
        String fullName = request.fullName();
        String username = request.username();

        if (!user.getDisplayName().equals(fullName)) {
            user.setDisplayName(fullName);
        }
        String userUsername = user.getUsername();
        if (!username.isEmpty() && !userUsername.equals(username)) {
            user.setUsername(username);
        }
    }

    private void userRegistration(JwtRequest authRequest, Long tgId) {
        userRegister.register(new RegisterRequest(tgId,
                checkUsernameAndReturnRandomIfEmpty(authRequest.username()),
                authRequest.fullName()));
    }

    private String checkUsernameAndReturnRandomIfEmpty(String username) {
        if (!username.isEmpty()) {
            return username;
        }
        String hash = HashUtils.generateRandomHash();

        return "tgUser" + hash;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
