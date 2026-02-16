package com.petshop.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthFilter implements GlobalFilter {

    private static final String SECRET_KEY =
            "mysecretkey12345mysecretkey12345";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        // Allow auth APIs
        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        // Read Authorization header
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        Claims claims;

        try {

            claims = Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(
                                    SECRET_KEY.getBytes(StandardCharsets.UTF_8)
                            )
                    )
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception e) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse().setComplete();
        }

        // Get user info
        String username = claims.getSubject();
        String role = claims.get("role", String.class);

        // ---------------- RBAC ----------------

        // Order Service: Only ADMIN
        if (path.startsWith("/api/orders")) {

            if (!"ADMIN".equals(role)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }
        }

        // Product Service
        if (path.startsWith("/api/products")) {

            if ("USER".equals(role) && !"GET".equals(method)) {

                exchange.getResponse()
                        .setStatusCode(HttpStatus.FORBIDDEN);

                return exchange.getResponse().setComplete();
            }
        }

        // Add headers
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(r -> r.header("X-USER-NAME", username)
                        .header("X-USER-ROLE", role))
                .build();

        // Forward request
        return chain.filter(mutatedExchange);
    }
}