package com.shivam.ecommerce.user_auth_service.service;

import com.shivam.ecommerce.user_auth_service.exceptions.NotFoundException;
import com.shivam.ecommerce.user_auth_service.model.User;
import com.shivam.ecommerce.user_auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves user details by their ID.
     *
     * @param userID The ID of the user to retrieve.
     * @return The user with the given ID.
     * @throws NotFoundException If the user with the specified ID is not found.
     */
    public User getUserDetails(Integer userID) {
        return userRepository.findById(userID).orElseThrow(() -> new NotFoundException("User not found with id: " + userID));
    }

    /**
     * Retrieves all users from the repository, sorted by ID in ascending order.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    /**
     * Creates a new user and encrypts their password before saving.
     *
     * @param user The user to be created, with a plain text password.
     * @return The saved user with the encrypted password.
     */
    public User generateUser(User user) {
        String passwordEncrypted = passwordEncoder.encode(user.getPassword());

        user.setPassword(passwordEncrypted);

        return userRepository.save(user);
    }
}