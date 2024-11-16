package com.hcmute.projectCT.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.HashMap;

@Service
public class JwtService {
    private String SECRET_KEY="1d2f67b6662a2f3e4350f1d9b240e81dfbd5be03c7f4d094ad1956d90a4003f0";
    public String getUserName(String jwtToken) {
        return extractAllClaims(jwtToken).getSubject();
    }
    public String generateToken(UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername()));
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