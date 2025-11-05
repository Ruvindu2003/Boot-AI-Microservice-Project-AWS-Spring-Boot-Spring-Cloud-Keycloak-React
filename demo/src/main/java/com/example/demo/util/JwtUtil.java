package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key signingKey;
    private final long expirationSeconds;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration-seconds:3600}") long expirationSeconds) {
        // ensure secret has enough length for HS256 (>= 256 bits)
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationSeconds = expirationSeconds;
    }

    public String generateToken(String subject, String role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationSeconds * 1000L);
        return Jwts.builder()
                .setSubject(subject)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public long getExpirationSeconds() {
        return this.expirationSeconds;
    }

    public String getRoleFromToken(String token) {
        if (token == null) return null;
        String raw = token.trim();
        if (raw.startsWith("Bearer ")) raw = raw.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(raw).getBody();
        Object role = claims.get("role");
        return role == null ? null : role.toString();
    }

    public String getSubjectFromToken(String token) {
        if (token == null) return null;
        String raw = token.trim();
        if (raw.startsWith("Bearer ")) raw = raw.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(raw).getBody();
        return claims.getSubject();
    }
}
