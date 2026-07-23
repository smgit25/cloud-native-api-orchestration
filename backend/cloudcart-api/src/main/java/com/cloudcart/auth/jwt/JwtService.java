package com.cloudcart.auth.jwt;

import com.cloudcart.customer.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Generates a signed JWT for the authenticated customer.
     */
    public String generateToken(Customer customer) {

        return Jwts.builder()
                .subject(customer.getEmail())
                .claim("customerId", customer.getCustomerId().toString())
                .claim("firstName", customer.getFirstName())
                .claim("lastName", customer.getLastName())
                .claim("role", customer.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extracts the username (email) from the JWT.
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT.
     */
    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts the role from the JWT.
     */
    public String extractRole(String token) {

        return extractClaim(
                token,
                claims -> claims.get("role", String.class)
        );
    }

    /**
     * Checks whether the JWT has expired.
     */
    public boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    /**
     * Validates the JWT against the authenticated user.
     */
    public boolean isTokenValid(String token, Customer customer) {

        String email = extractUsername(token);

        return email.equals(customer.getEmail())
                && !isTokenExpired(token);
    }

    /**
     * Generic method to extract any claim from the JWT.
     */
    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Parses the JWT, verifies its signature, and returns all claims.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Returns the signing key used to sign and verify JWTs.
     */
    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}