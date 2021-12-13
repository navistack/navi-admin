package org.navistack.admin.config;

import org.navistack.boot.autoconfigure.security.AuthTokenConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthTokenConfigurer authTokenConfigurer
    ) throws Exception {
        // @formatter:off
        return http
                .authorizeRequests()
                    .antMatchers("/login")
                        .anonymous()
                    .antMatchers("/doc.html", "/webjars/**", "/swagger-resources", "/v3/api-docs")
                        .anonymous()
                    .antMatchers("/sys/simple-captcha/**")
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
                .apply(authTokenConfigurer)
                .and()
                .build();
        // @formatter:on
    }
}
