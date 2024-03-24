package ru.gb.backend.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gb.backend.services.IJwtService;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements IJwtService {

    @Value("${security.jwt.secretKey}")
    private String secretKey;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public String extractUsername(String token) {
        log.info("IN extractUsername - extracted username by token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        log.info("IN generateToken - for user with username: {} generated token", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        log.info("IN isTokenValid - checked validity token: {} for user with username: {}", token, userDetails.getUsername());
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        log.info("IN isTokenExpired - checked expiration token: {}", token);
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        log.info("IN extractExpiration - extracted expiration token: {}", token);
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority("ADMIN"))) {
            extraClaims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("USER"))) {
            extraClaims.put("isUser", true);
        }
        log.info("IN generateToken - for user with username: {} and role: {} generated token", userDetails.getUsername(), userDetails.getAuthorities());
        return Jwts.builder().claims().subject(userDetails.getUsername()).issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .and().signWith(getSignInKey()).compact();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        log.info("IN extractClaim - extracted claims by token: {}", token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("IN extractAllClaims - extracted all claims by token: {}", token);
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        log.info("IN egetSignInKey - got sign in key");
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
