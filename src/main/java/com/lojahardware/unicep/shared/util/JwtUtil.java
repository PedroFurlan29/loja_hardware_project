package com.lojahardware.unicep.shared.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.*;

@Component @Slf4j
public class JwtUtil {
    @Value("${jwt.secret}") private String jwtSecret;
    @Value("${jwt.expiration}") private long jwtExpirationMs;
    private SecretKey getSigningKey(){ return Keys.hmacShaKeyFor(jwtSecret.getBytes()); }
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        claims.put("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return createToken(claims,userDetails.getUsername());
    }
    private String createToken(Map<String,Object> claims,String subject){
        Date now=new Date();
        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(now).setExpiration(new Date(now.getTime()+jwtExpirationMs))
            .signWith(getSigningKey(),SignatureAlgorithm.HS256).compact();
    }
    public String getEmailFromToken(String token){ return getClaims(token).getSubject(); }
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token){
        return (List<String>)getClaims(token).get("roles",List.class);
    }
    public boolean validateToken(String token){
        try{ getClaims(token); return true; }
        catch(Exception e){ log.error("JWT validation failed: {}",e.getMessage()); return false; }
    }
    public boolean isTokenExpired(String token){
        try{ return getClaims(token).getExpiration().before(new Date()); }
        catch(Exception e){ return true; }
    }
    private Claims getClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build()
            .parseClaimsJws(token).getBody();
    }
}
