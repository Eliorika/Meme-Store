package dev.zekfad.rsreu.carservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;

@Component
public class JwtService {
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
				.claims(claims)
				.subject(userName)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), Jwts.SIG.HS256).compact();
	}

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public @Nullable String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public @Nullable Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public @Nullable <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims;
		try {
			claims = extractAllClaims(token);
		} catch (ExpiredJwtException e) {
			claims = e.getClaims();
		} catch (Exception e) {
			return null;
		}
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
