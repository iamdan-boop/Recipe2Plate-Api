package com.recipe2plate.api.security;


import com.recipe2plate.api.dto.request.auth.LoginRequest;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;


    private final AuthenticationService authenticationService;

    public String generateToken(String email) {
        final Claims claims = Jwts.claims().setSubject(email);
        final Date issuedAt = new Date();
        final Date validUntil = new Date(issuedAt.getTime() + 3600000);
        final SecretKey buildSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(validUntil)
                .signWith(buildSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public Authentication validateCredentials(LoginRequest loginRequest) {
        final AppUser appUser = authenticationService.authenticate(loginRequest);
        return new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                List.of((GrantedAuthority) () -> (appUser.getRole().getRoleName()))
        );
    }


    public Authentication validateToken(String authToken) {
        final String email = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
        final AppUser appUser = authenticationService.findByEmail(email);
        return new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                List.of((GrantedAuthority) () -> (appUser.getRole().getRoleName()))
        );
    }
}
