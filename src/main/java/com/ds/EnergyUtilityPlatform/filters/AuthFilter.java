package com.ds.EnergyUtilityPlatform.filters;

import com.ds.EnergyUtilityPlatform.model.dto.UserDetails;
import com.ds.EnergyUtilityPlatform.utils.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(2)
public class AuthFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            username = jwtUtility.getUsernameFromToken(jwtToken);
        }

        if(jwtToken == null) {
            response.setStatus(401);
            response.getOutputStream().write("JWT token is missing!".getBytes());
            return;
        }

        if(username != null) {
            UserDetails userDetails = new UserDetails(username, "");
            if(!jwtUtility.validateToken(jwtToken, userDetails)) {
                response.setStatus(401);
                response.getOutputStream().write("JWT token is invalid!".getBytes());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return "/api/user/login".equals(path);
    }
}

