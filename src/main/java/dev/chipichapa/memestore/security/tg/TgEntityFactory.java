package dev.chipichapa.memestore.security.tg;

import dev.chipichapa.memestore.domain.entity.user.Role;
import dev.chipichapa.memestore.domain.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TgEntityFactory {

    public static TgEntity create(final User user) {
        return new TgEntity(user.getId(),
                user.getTgId(),
                user.getUsername(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(final List<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
