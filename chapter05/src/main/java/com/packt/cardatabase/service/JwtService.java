package com.packt.cardatabase.service;

import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
// import java.security.Key;
import javax.crypto.SecretKey;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtService {
    static final long EXPIRATIONTIME = 86400000;
    // 1 day in ms. Should be shorter in production.
    static final String PREFIX = "Bearer";

    // Generate secret key. Only for demonstration purposes.
    // In production, you should read it from the application
    // configuration.
    // static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final SecretKey key = Keys.hmacShaKeyFor(
            "IhrMindestens256BitLangerGeheimerSchluesselHier...".getBytes());

    // Generate signed JWT token
    public String getToken(String username) {
        String token = Jwts.builder().subject(username)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)).signWith(key).compact();
        return token;
    }

    // Get a token from request Authorization header,
    // verify the token, and get username
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            // String user =
            // Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token.replace(PREFIX,
            // ""))
            // .getBody().getSubject();
            String user = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token.replace(PREFIX, ""))
                    .getPayload()
                    .getSubject();

            if (user != null)
                return user;
        }
        return null;
    }
}
