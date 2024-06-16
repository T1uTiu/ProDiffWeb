package com.tiutiu.prodiff.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static String createJwt(String secretkey, Long ttl, Map<String, Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long expireMills = System.currentTimeMillis() + ttl;
        Date expireDate = new Date(expireMills);

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setExpiration(expireDate)
                .signWith(signatureAlgorithm, secretkey.getBytes(StandardCharsets.UTF_8));
        return builder.compact();
    }

    public static Claims parseJwt(String secretkey, String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(secretkey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }

    }
}
