package com.micro.discussions.service_auth.filters;

import com.micro.discussions.service_auth.CustomAuthenticationToken;
import com.micro.discussions.service_auth.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

public class HeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String userRole = request.getHeader("X-User-Role");
        String userName = request.getHeader("X-User-Name");

        if (userRole != null && userName != null){
            CustomUserDetails userDetails = new CustomUserDetails(userName, userRole);
            CustomAuthenticationToken authentication = new CustomAuthenticationToken(userDetails);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
