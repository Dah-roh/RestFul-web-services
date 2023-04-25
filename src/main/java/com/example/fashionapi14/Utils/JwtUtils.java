package com.example.fashionapi14.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    @Value("${jwt.expiration}")
    private String expiration;

    public static String generateSecret(){
        return DatatypeConverter.printBase64Binary(new byte[512/8]);
    }


    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims =  new HashMap<>();
        claims.put("Role", userDetails.getAuthorities());
        return createToken(claims, userDetails.getUsername());
    }

    public static Key generateKey(){
        byte[] secretKeyInBytes = DatatypeConverter.parseBase64Binary(generateSecret());
        return new SecretKeySpec(secretKeyInBytes, "HmacSHA512");
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }



    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(expiration)))
                .signWith(generateKey(), SignatureAlgorithm.HS512)
                .compact();

    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }
}
