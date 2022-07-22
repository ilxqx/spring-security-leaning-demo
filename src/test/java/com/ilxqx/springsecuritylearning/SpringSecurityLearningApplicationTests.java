package com.ilxqx.springsecuritylearning;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

class SpringSecurityLearningApplicationTests {

    @Test
    void contextLoads() {
        String password = "123456";

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("matches password");
        passwordEncoder.matches(password, encodedPassword);
        stopWatch.stop();

        System.out.println(stopWatch.getLastTaskTimeMillis());
    }

    @Test
    public void testJwt() {
        String secret = UUID.randomUUID().toString();
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
            .setExpiration(new Date(
                System.currentTimeMillis() + Duration.ofMinutes(2).toMillis()
            ))
            .claim("userId", "123")
            .claim("name", "张三")
            .signWith(secretKey)
            .compact();

        System.out.println(token);

        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);

        System.out.println(claimsJws.getBody());
    }
}
