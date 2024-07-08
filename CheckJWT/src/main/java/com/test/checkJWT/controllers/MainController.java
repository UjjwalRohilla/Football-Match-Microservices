package com.test.checkJWT.controllers;

import com.test.checkJWT.customExceptions.TokenEmptyException;
import com.test.checkJWT.securityConstants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.Base64;

@RestController
@RequestMapping("/check_jwt")
public class MainController {

    @PostMapping("/get/payload")
    public ResponseEntity<?> getJwtDetails(@RequestBody String token){
        if(!token.isEmpty()){
            SecretKey key = generateKey();
            if(token.startsWith("Bearer ")){
                token = token.substring(7);
            }
            try{
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
                return ResponseEntity.ok(claims);
            }catch (Exception  e){
                e.printStackTrace();
            }
        }else {
            throw new TokenEmptyException("Invalid token!");
        }
        return ResponseEntity.ok(null);
    }
    private SecretKey generateKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SecurityConstants.JWT_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
