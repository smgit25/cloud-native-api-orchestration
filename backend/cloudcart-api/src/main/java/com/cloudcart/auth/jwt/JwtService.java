package com.cloudcart.auth.jwt;

import com.cloudcart.customer.entity.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET =
            "your-256-bit-secret-key-goes-here";


        // JWT generation logic
        @Value("${jwt.secret}")
        private String secret;

        @Value("${jwt.expiration}")
        private long expiration;

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

        private SecretKey getSigningKey() {

            byte[] keyBytes = Decoders.BASE64.decode(secret);

            return Keys.hmacShaKeyFor(keyBytes);
        }

    public String extractUsername(String token) {
        //
        return "hello";
    }

    public boolean isTokenValid(String token, Customer customer) {
        //...
        return false;
    }

    public boolean isTokenExpired(String token) {
        //
        return false;
    }
}
