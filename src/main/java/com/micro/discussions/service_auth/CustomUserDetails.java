package com.micro.discussions.service_auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomUserDetails {
    private final String username;
    private final String role;
}
