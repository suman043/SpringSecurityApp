package com.exampleSecurityApp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //private String secret = "mySecretKey";

    // Generate a strong key
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                //.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiry

                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 10 minutes expiry

                //.signWith(SignatureAlgorithm.HS256, secret)
                .signWith(key) // Use secure key
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String email) {
        return email.equals(extractEmail(token)) && !isTokenExpired(token);
    }

    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();

        return Jwts.parserBuilder()
                .setSigningKey(key) // Use secure key for parsing
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}