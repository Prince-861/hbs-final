package com.hms.hotel_booking_system.config;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hms.hotel_booking_system.entity.AppUser;
import com.hms.hotel_booking_system.repository.AppUserRepository;
import com.hms.hotel_booking_system.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final AppUserRepository userRepository;
    private final ObjectMapper objectMapper;

    public JWTFilter(JWTService jwtService, AppUserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenVal = token.substring(8,token.length()-1);

        try {
            String username = jwtService.getUsername(tokenVal);
            Optional<AppUser> opUsername = userRepository.findByUsername(username);
            if (opUsername.isPresent()) {
                AppUser appUser = opUsername.get();
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(appUser, null,
                                Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (TokenExpiredException ex) {
            setErrorResponse(response, request, HttpServletResponse.SC_UNAUTHORIZED, "JWT token has expired");
            return;
        } catch (JWTDecodeException ex) {
            setErrorResponse(response, request, HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token");
            return;
        } catch (SignatureVerificationException ex) {
            setErrorResponse(response, request, HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, HttpServletRequest request, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");

        Map<String, Object> errorDetails = new LinkedHashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", status);
        errorDetails.put("error", message);
        errorDetails.put("path", request.getRequestURI());

        String jsonErrorResponse = objectMapper.writeValueAsString(errorDetails);
        response.getWriter().write(jsonErrorResponse);
    }
}
