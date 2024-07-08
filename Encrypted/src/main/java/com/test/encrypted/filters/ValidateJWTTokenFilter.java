package com.test.encrypted.filters;

import com.test.encrypted.securityConstants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;

public class ValidateJWTTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String rawToken = request.getHeader(SecurityConstants.AUTHORIZATION);

        String username = null;
        String jwt_token = null;

        if(rawToken != null && rawToken.startsWith(SecurityConstants.BEARER)){
            try{
                SecretKey secretKey = generateSecretKey();

                jwt_token = rawToken.substring(7);

                Claims claims = Jwts.parser().verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt_token)
                        .getPayload();
                username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");

                Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        filterChain.doFilter(request,response);
    }

    private SecretKey generateSecretKey() {
        byte[] keyByte = Base64.getDecoder().decode(SecurityConstants.JWT_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
