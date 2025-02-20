package com.openclassrooms.PayMyBuddy.security.config;

import com.openclassrooms.PayMyBuddy.security.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring security configuration
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor
     *
     * @param customUserDetailsService customUserDetailsService
     */
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Security filter for routes
     *
     * @param httpSecurity httpSecurity
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("====> Loading Spring Security : SecurityFilterChain <====");
        return httpSecurity.authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/login", "/register", "/logout", "/", "/image/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .usernameParameter("email")
                                .defaultSuccessUrl("/transfer", true)
                                .permitAll()
                ).build();

    }

    /**
     * Password encoder with BCrypt
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.info("====> Loading Spring Security : BCryptPasswordEncoder <====");
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication manager
     *
     * @param http                  http
     * @param bCryptPasswordEncoder bCryptPasswordEncoder
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        log.info("====> Loading Spring Security : AuthenticationManager <====");
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }


}
