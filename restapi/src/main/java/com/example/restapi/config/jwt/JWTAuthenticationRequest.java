package com.example.restapi.config.jwt;

import lombok.Data;

@Data
public class JWTAuthenticationRequest {
    private String username;
    private String password;
}
