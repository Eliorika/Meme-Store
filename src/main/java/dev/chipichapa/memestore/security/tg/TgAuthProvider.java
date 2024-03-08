package dev.chipichapa.memestore.security.tg;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.service.ifc.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TgAuthProvider {

    private final UserService userService;

    public boolean authenticate(Long tgId) {
        User user = userService.getByTgId(tgId);

        TgEntity tgUser = TgEntityFactory.create(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(tgUser,
                null,
                tgUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;
    }
}
