package com.cache.springcache.config;

import com.cache.springcache.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        we need to inform our http to authenticate the which api calls i need to authenticate , otherwise it will not do authentication and allow every request
        http
//                authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**","/api/**")
//                        .permitAll().anyRequest().authenticated())
//                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").permitAll())
//                        .httpBasic(Customizer.withDefaults());
        // Configure CSRF - disable for API endpoints and H2 console
            .csrf(csrf -> csrf
                .ignoringRequestMatchers( "/h2-console/**","/api/**")
        )
                // Configure frame options for H2 console
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Permit H2 console and API endpoints
                        .requestMatchers("/h2-console/**").permitAll()
                        // Only require authentication for /app/** endpoints
                        .requestMatchers("/app/**").authenticated()
                        // Allow all other requests (if any exist outside /app and /api)
                        .anyRequest().permitAll()
                )
                // Configure HTTP Basic authentication
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public CustomUserDetailsService  customUserDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(CustomUserDetailsService customUserDetailsService,PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}
