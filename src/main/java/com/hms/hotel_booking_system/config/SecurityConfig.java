package com.hms.hotel_booking_system.config;

import com.hms.hotel_booking_system.exception.CustomAccessDeniedHandler;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private final JWTFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JWTFilter jwtFilter, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests().anyRequest().permitAll();

//        http.authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/users/login", "/api/v1/users/signup", "/api/v1/users/signup-property-owner")
//                        .permitAll()
//                        .requestMatchers("/api/v1/country/addCountry")
//                        .hasAnyRole("OWNER", "ADMIN")
//                        .anyRequest()
//                        .authenticated()
//                )
//                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }
}
