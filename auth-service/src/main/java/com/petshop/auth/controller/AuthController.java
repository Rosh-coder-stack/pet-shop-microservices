package com.petshop.auth.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        // TEMP check
        if ("admin".equals(username) && "admin".equals(password)) {
            return Map.of("token", "DUMMY-JWT-TOKEN");
        }

        throw new RuntimeException("Invalid credentials");
    }
}