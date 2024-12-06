package com.example.decentralizedstorage.service;

import com.example.decentralizedstorage.entity.User;
import com.example.decentralizedstorage.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;  // If you haven't actually registered any users with your existing format then you would be best to switch to using the BCrypt password encoder instead.

    // DI,  uses constructor injection to receive its dependencies
    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username).isPresent() ||
                userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Username or Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        return userRepository.save(user);
    }
}
