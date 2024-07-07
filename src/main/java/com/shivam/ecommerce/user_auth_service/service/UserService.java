package com.shivam.ecommerce.user_auth_service.service;

import com.shivam.ecommerce.user_auth_service.jwt.JwtUtils;
import com.shivam.ecommerce.user_auth_service.model.SigninRequest;
import com.shivam.ecommerce.user_auth_service.model.User;
import com.shivam.ecommerce.user_auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public User getUserDetails(Integer userID) {
        return userRepository.findById(userID).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User generateUser(User user) {
        String passwordEncrypted = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordEncrypted);

        return userRepository.save(user);
    }
}