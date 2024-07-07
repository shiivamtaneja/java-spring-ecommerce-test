package com.shivam.ecommerce.user_auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SigninResponse {
    private String jwtToken;

    private String username;
    private List<String> roles;
}
