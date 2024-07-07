package com.shivam.ecommerce.user_auth_service.model;

import lombok.Data;

@Data
public class SigninRequest {
    private String username;
    private String password;
}
