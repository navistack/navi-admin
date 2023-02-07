package org.navistack.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        return http
                .headers()
                    .xssProtection()
                    .and()
                    .contentSecurityPolicy("script-src 'self'")
                    .and()
                .and()
                .authorizeRequests()
                    .requestMatchers("/login")
                        .anonymous()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                        .anonymous()
                    .requestMatchers("/sys/simple-captcha/**")
                        .anonymous()
                    .anyRequest()
                        .authenticated()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                    .disable()
                .httpBasic()
                    .disable()
                .formLogin()
                    .disable()
                .logout()
                    .disable()
                .build();
        // @formatter:on
    }
}
