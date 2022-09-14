package com.example.community_board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.   csrf().
                disable().

                sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS).

                and().
                authorizeRequests().
                antMatchers("/api/**").permitAll();

        return http.build();
    }
}
