package com.example.springdevkpi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    public String generateToken(String login) {
        return JWT.create()
                .withIssuer(jwtIssuer)
                .withSubject(login)
                .withExpiresAt(LocalDate
                        .now()
                        .plusDays(15)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant())
                .sign(Algorithm
                        .HMAC256(jwtSecret));
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm
                            .HMAC256(jwtSecret))
                    .withIssuer(jwtIssuer)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            log.warn("Invalid protocol for token {}", token);
            return false;
        }
    }

    public String getLoginFromToken(String token) {
        return JWT
                .require(Algorithm
                        .HMAC256(jwtSecret))
                .withIssuer(jwtIssuer)
                .build()
                .verify(token)
                .getSubject();
    }


}