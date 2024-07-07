package com.shivam.ecommerce.user_auth_service.controller;

import com.shivam.ecommerce.user_auth_service.jwt.JwtUtils;
import com.shivam.ecommerce.user_auth_service.model.ApiResponse;
import com.shivam.ecommerce.user_auth_service.model.SigninRequest;
import com.shivam.ecommerce.user_auth_service.model.SigninResponse;
import com.shivam.ecommerce.user_auth_service.model.User;
import com.shivam.ecommerce.user_auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<User>> getUserDetails(@PathVariable Integer id) {
        try {
            User userDetails = userService.getUserDetails(id);

            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "User Details Found!", userDetails);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> userDetails = userService.getAllUsers();

            ApiResponse<List<User>> response = new ApiResponse<>(HttpStatus.OK.value(), "User Details Found!", userDetails);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> userSingup(@RequestBody User userDetails) {
        try {
            User createdUser = userService.generateUser(userDetails);

            ApiResponse<User> response = new ApiResponse<>(HttpStatus.CREATED.value(), "User Details Found!", createdUser);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SigninResponse>> userSingin(@RequestBody SigninRequest request) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
            ApiResponse<SigninResponse> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Bad credentials!", null);

            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtils.generateTokenFromUsername(request.getUsername());

        SigninResponse signinResponse = new SigninResponse(token, request.getUsername(), null);

        ApiResponse<SigninResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "Successful!", signinResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
