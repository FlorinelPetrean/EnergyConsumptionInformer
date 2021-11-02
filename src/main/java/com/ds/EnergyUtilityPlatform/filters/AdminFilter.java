package com.ds.EnergyUtilityPlatform.filters;

import com.ds.EnergyUtilityPlatform.model.entity.AppUser;
import com.ds.EnergyUtilityPlatform.service.UserService;
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
@Order(3)
public class AdminFilter extends OncePerRequestFilter {
    private final JwtUtility jwtUtility;
    private final UserService userService;
    private AppUser user;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = authorizationHeader.substring(7);
        String username = jwtUtility.getUsernameFromToken(jwtToken);


        if(username != null) {
            user = userService.findByUsername(username);
            if (!user.getRole().equals("ADMIN")) {
                if(!request.getMethod().equals("GET")) {
                    response.setStatus(401);
                    response.getOutputStream().write("User is not admin! Operation is not permitted".getBytes());
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        //String userId = String.valueOf(user.getId());
        if (path.equals("/api/user/create"))
            return true;
        if (path.equals("/api/user/login"))
            return true;
        return false;
    }
}

