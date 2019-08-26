package com.apogee.trackarea.config;


import com.apogee.trackarea.db.pojo.UserPojo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

//    @Value("#{new Integer('${app.jwtExpiration}')}")
    @Value(("${app.jwtExpiration}"))
    private int jwtExpiration;

    public String generateToken(Authentication authentication){
        UserPojo details = (UserPojo) authentication.getPrincipal();

        Date now = new Date();
        Date expiry = new Date(now.getTime()+ jwtExpiration);

        return Jwts.builder()
            .setSubject(details.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret)
                        .parseClaimsJws(token)
                        .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (Exception e){
            log.error("JWT Token could not be validated");
        }
        return false;
    }
}
