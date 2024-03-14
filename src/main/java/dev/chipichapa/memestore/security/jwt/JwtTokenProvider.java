package dev.chipichapa.memestore.security.jwt;

import dev.chipichapa.memestore.domain.entity.user.Role;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.auth.JwtResponse;
import dev.chipichapa.memestore.security.exception.AccessDeniedException;
import dev.chipichapa.memestore.security.jwt.properties.JwtProperties;
import dev.chipichapa.memestore.security.tg.TgAuthProvider;
import dev.chipichapa.memestore.security.tg.TgEntityFactory;
import dev.chipichapa.memestore.service.ifc.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserService userService;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, Long tgId, Set<Role> roles) {
        Claims claims = Jwts.claims()
                .subject(String.valueOf(tgId))
                .add("id", userId)
                .add("roles", resolveRoles(roles))
                .build();

        Instant validity = Instant.now().plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(secretKey)
                .compact();
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public String createRefreshToken(Long userId, Long tgId) {
        Claims claims = Jwts.claims()
                .subject(String.valueOf(tgId))
                .add("id", userId)
                .build();
        Instant validity = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(secretKey)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (!isValid(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = getId(refreshToken);
        User user = userService.getById(userId);

        jwtResponse.setId(userId);
        jwtResponse.setTgId(user.getTgId());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getTgId(), user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, user.getTgId()));
        return jwtResponse;
    }

    public boolean isValid(String token) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }

    private Long getId(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    private String getTgId(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String tgId = getTgId(token);
        User user = userService.getByTgId(Long.valueOf(tgId));
        UserDetails userDetails =  TgEntityFactory.create(user);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
