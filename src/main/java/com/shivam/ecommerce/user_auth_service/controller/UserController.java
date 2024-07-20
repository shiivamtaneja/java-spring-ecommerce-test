package com.shivam.ecommerce.user_auth_service.controller;

import com.shivam.ecommerce.user_auth_service.exceptions.NotFoundException;
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

    /**
     * Handles GET requests to retrieve a specific user by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing an ApiResponse with user details and HTTP status 200 (OK) if found,
     * or HTTP status 404 (Not Found) if the user does not exist.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<User>> getUserDetails(@PathVariable Integer id) {
        User userDetails = userService.getUserDetails(id);

        ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), "User Details Found!", userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles GET requests to retrieve all users.
     * <p>
     * This method fetches a list of all users from the user service. If no users are found,
     * it throws a NotFoundException. Otherwise, it returns a list of users with an HTTP status of 200 (OK).
     *
     * @return A ResponseEntity containing an ApiResponse with the list of users and HTTP status 200 (OK) if users are found,
     * or HTTP status 404 (Not Found) if no users are found.
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> userDetails = userService.getAllUsers();

        if (userDetails.isEmpty()) {
            throw new NotFoundException("No users found");
        }

        ApiResponse<List<User>> response = new ApiResponse<>(HttpStatus.OK.value(), "User Details Found!", userDetails);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles POST requests to sign up a new user.
     * <p>
     * This method creates a new user with the details provided in the request body. It returns the created user
     * with an HTTP status of 201 (Created).
     *
     * @param userDetails The request body containing the user details for sign up.
     * @return A ResponseEntity containing an ApiResponse with the created user and HTTP status 201 (Created).
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> userSingup(@RequestBody User userDetails) {
        User createdUser = userService.generateUser(userDetails);

        ApiResponse<User> response = new ApiResponse<>(HttpStatus.CREATED.value(), "User Details Found!", createdUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Handles POST requests to sign in a user.
     * <p>
     * This method authenticates the user with the provided username and password. Upon successful authentication,
     * it generates a JWT token for the user and returns it along with the username in the response with HTTP status 200 (OK).
     *
     * @param request The request body containing the username and password for sign in.
     * @return A ResponseEntity containing an ApiResponse with the JWT token, username, and HTTP status 200 (OK) if sign in is successful.
     */
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SigninResponse>> userSingin(@RequestBody SigninRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtUtils.generateTokenFromUsername(request.getUsername());

        SigninResponse signinResponse = new SigninResponse(token, request.getUsername(), null);

        ApiResponse<SigninResponse> response = new ApiResponse<>(HttpStatus.OK.value(), "Successful!", signinResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
