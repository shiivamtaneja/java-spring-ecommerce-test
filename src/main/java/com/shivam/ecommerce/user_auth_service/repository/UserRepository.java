package com.shivam.ecommerce.user_auth_service.repository;

import com.shivam.ecommerce.user_auth_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}