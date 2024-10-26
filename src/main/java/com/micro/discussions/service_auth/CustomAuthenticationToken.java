package com.micro.discussions.service_auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {
    private final CustomUserDetails principal;

    public CustomAuthenticationToken(CustomUserDetails principal) {
        super(List.of(new SimpleGrantedAuthority(principal.getRole())));
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(principal.getRole()));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public CustomUserDetails getPrincipal() {
        return principal;
    }
}
