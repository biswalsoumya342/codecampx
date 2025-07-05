package com.codecampx.codecampx.security;

import com.codecampx.codecampx.security.impl.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private UserDetailServiceImpl userDetailService;

    public JwtService(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userName){
        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        Map<String,Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("ROLE_USER");
        claims.put("role",role);
        return createToken(claims,userName);
    }

    public String createToken(Map<String,Object>claims,String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claim = extractAllClaims(token);
        return claimResolver.apply(claim);
    }

    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);
    }



    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}
