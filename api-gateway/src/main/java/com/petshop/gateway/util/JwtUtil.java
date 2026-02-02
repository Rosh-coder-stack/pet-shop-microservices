package com.petshop.gateway.util;

import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static final String SECRET_KEY = "mysecretkey12345";

    public static void validateToken(String token) {
        Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }
}
