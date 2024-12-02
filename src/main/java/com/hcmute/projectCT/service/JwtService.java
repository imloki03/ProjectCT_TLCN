package com.hcmute.projectCT.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;
    public String getUserName(String jwtToken) {
        return extractAllClaims(jwtToken).getSubject();
    }
    public String generateToken(UserDetails userDetails, long expiredTimeMil){
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMil))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken){
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}