package com.example.decentralizedstorage.controller;

import com.example.decentralizedstorage.entity.User;
import com.example.decentralizedstorage.service.AuthService;
import com.example.decentralizedstorage.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String email,
                                           @RequestParam String password) {
        authService.registerUser(username, email, password);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        // Dummy authentication logic for now
        String token = JwtUtil.generateToken(username);
        return ResponseEntity.ok(token);
    }
}
