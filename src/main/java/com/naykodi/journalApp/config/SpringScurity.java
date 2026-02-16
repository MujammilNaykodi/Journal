package com.naykodi.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.naykodi.journalApp.service.UserDetailsImplementation;

@Configuration
public class SpringScurity {
@Autowired
private UserDetailsImplementation userDetailsService;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth

            // protect journal and user endpoints
            .requestMatchers("/journal/**", "/users/**").authenticated()
            // only admin can access admin endpoints
            .requestMatchers("/admin/**").hasRole("ADMIN")

            // everything else is public
            .anyRequest().permitAll()
        )
        .httpBasic(httpBasic -> {})
        .userDetailsService(userDetailsService); // attach service here

    return http.build();
}

// Password encoder
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

}
