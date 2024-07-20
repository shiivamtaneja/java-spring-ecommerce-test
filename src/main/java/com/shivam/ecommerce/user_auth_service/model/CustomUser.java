package com.shivam.ecommerce.user_auth_service.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of the UserDetails interface.
 * Used to provide user-specific data to Spring Security.
 */
public class CustomUser implements UserDetails {
    private final String username;
    private final String password;

    /**
     * Constructs a CustomUser object from a User entity.
     *
     * @param user The User entity containing user details.
     */
    public CustomUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    /**
     * Returns the authorities granted to the user.
     * In this implementation, authorities are not used.
     *
     * @return An empty collection as authorities are not supported in this implementation.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Returns the user's password.
     *
     * @return The password of the user.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's username.
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return username;
    }
}
