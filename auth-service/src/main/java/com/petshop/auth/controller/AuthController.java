package com.petshop.auth.controller;
import com.petshop.auth.util.JwtUtil;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.petshop.auth.util.JwtUtil;
import com.petshop.auth.entity.User;
import com.petshop.auth.repository.UserRepository;



@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepo;

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody Map<String,String> req){

        String username = req.get("username");
        String password = req.get("password");

        User user = userRepo.findByUsername(username);

        if(user == null || !user.getPassword().equals(password)){
            throw new RuntimeException("Invalid login");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );

        return Map.of("token", token);

    }

}