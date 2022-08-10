package com.recipe2plate.api.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;


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


    public String validateTokenWithSubject(String authToken) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(authToken)
                .getBody()
                .getSubject();
    }
}
