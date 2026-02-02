package com.petshop.auth.controller;
import com.petshop.auth.util.JwtUtil;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.petshop.auth.util.JwtUtil;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        // TEMP check
        if ("admin".equals(username) && "admin".equals(password)) {
            String role = "USER";   // for now (simple)

            String token = jwtUtil.generateToken(username, role);

            return Map.of("token", token);
        }

        throw new RuntimeException("Invalid credentials");
    }
}