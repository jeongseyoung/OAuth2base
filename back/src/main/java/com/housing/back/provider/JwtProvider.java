package com.housing.back.provider;

import java.util.*;
import java.time.*;
import java.time.temporal.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.util.StandardCharset;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.*;

@Component
public class JwtProvider {

    // @Value("${secretkey}")
    private String secretKey = "scLSDnbLSDKFJlkdfsdlfksSDFkXCIcvlkq3mMOoei32$#@SDaaoeEHjzXCVeqsivDghO";

    // 토큰생성
    public String create(String userId) {
        Date expireDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharset.UTF_8));

        String jwt = Jwts.builder().signWith(key, SignatureAlgorithm.HS256).setSubject(userId).setIssuedAt(new Date())
                .setExpiration(expireDate).compact();

        return jwt;
    }

    // 토큰 유효성 체크
    public String validate(String jwt) {
        Claims claims = null;
        String subject = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharset.UTF_8));
        try {
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            subject = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return subject;
    }
}
