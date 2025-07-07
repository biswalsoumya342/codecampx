package com.codecampx.codecampx.security;

import com.codecampx.codecampx.security.impl.UserDetailServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

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
        String token = createToken(claims,userName);
        if (token != null) logger.info("Token generate successful for Username: {}",userName);
        else logger.info("Token generate Unsuccessful for Username: {}",userName);
        return token;
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

    //Extracting Cliams
    //Extract All Claims From Token
    public Claims extractAllClaims(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        logger.info("All Claims Extracting");
        return claims;
    }

    //Extract Claim using Token
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        Claims claim = extractAllClaims(token);
        T t = claimResolver.apply(claim);
        logger.info("Extracting claim from all claim");
        return t;
    }

    //Extract UserName From The Claim
    public String extractUserName(String token) {
        String userName = extractClaim(token,Claims::getSubject);
        if (userName == null) logger.debug("UserName Extraction From Token Unsuccessful: {}",userName);
        else logger.debug("UserName Extraction Successful From Token : {}",userName);
        return userName;
    }


    //Validating Token
    //Extract Expairation
    public Date extractExpiration(String token){
        Date date = extractClaim(token,Claims::getExpiration);
        logger.info("Expiration Of Token extracting");
        return date;
    }

    //Check Is Token Expired Or Not
    public boolean isTokenExpired(String token){
        boolean status = extractExpiration(token).before(new Date());
        logger.info("Checking Token Expired Or Not");
        return status;
    }

    //Validate Token With UserName
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = extractUserName(token);
        boolean status = (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
        logger.info("Matching Token With UserName");
        return status;
    }


    //Extract Role Of The User From Token
    public String extractRole(String token){
        String role = extractAllClaims(token).get("role",String.class);
        logger.info("Role Of The User Extracting...");
        return role;
    }
}
