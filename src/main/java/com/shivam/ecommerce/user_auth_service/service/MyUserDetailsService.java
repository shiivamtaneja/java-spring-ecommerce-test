package com.shivam.ecommerce.user_auth_service.service;

import com.shivam.ecommerce.user_auth_service.model.User;
import com.shivam.ecommerce.user_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Custom implementation of the UserDetailsService interface.
 * Provides a way to retrieve user details for authentication and authorization purposes.
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Loads user-specific data by username for authentication.
     *
     * @param username The username of the user to retrieve.
     * @return A UserDetails object containing user information required for authentication.
     * @throws UsernameNotFoundException If no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails = userRepository.findByUsername(username);

        if (userDetails != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userDetails.getUsername())
                    .password(userDetails.getPassword())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
