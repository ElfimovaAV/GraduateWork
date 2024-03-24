package ru.gb.backend.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface IJwtService {
    public String extractUsername(String token);
    public boolean isTokenValid(String token, UserDetails userDetails);
    public String generateToken(UserDetails userDetails);
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails);
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver);
}
