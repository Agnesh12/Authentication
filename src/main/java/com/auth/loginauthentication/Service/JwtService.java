package com.auth.loginauthentication.Service;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 15;
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60 * 24 * 7;
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
                .signWith(key)
                .compact();
    }
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isTokenValid(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;

        }
        catch(JwtException || IllegalArgumentException e) {
            return false;
        }
    }

}
