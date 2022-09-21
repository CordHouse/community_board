package com.example.community_board.config;

import com.example.community_board.jwt.JwtAccessDeniedHandler;
import com.example.community_board.jwt.JwtEntryPointHandler;
import com.example.community_board.jwt.JwtSecureConfig;
import com.example.community_board.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtEntryPointHandler jwtEntryPointHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.   csrf().disable().
                exceptionHandling().
                accessDeniedHandler(jwtAccessDeniedHandler).
                authenticationEntryPoint(jwtEntryPointHandler).

                and().
                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).

                and().
                authorizeRequests().
                antMatchers("/api/admin").access("hasRole('ROLE_ADMIN')").
                antMatchers("/api/auth/**").access("hasRole('ROLE_USER')").
                antMatchers("/api/**").permitAll().

                and().
                apply(new JwtSecureConfig(tokenProvider));

        return http.build();
    }
}
