package com.test.generate.jwtService;

import com.test.generate.model.User;
import com.test.generate.securityConstants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.get("username").toString();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername())) && isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public String generateToken(User user){
        return Jwts
                .builder()
                .issuer("Token issuer")
                .subject("Token Generation")
                .claim("username",user.getEmail())
                .claim("authorities",user.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+(24*60*60*1000)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SecurityConstants.JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
