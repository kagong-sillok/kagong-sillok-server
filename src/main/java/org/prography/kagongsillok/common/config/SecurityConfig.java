package org.prography.kagongsillok.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(
                        "/h2-console/**",
                        "/api/**",
                        "/admin/**",
                        "/swagger-ui/**",
                        "/swagger-ui.**",
                        "/v3/api-docs/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated();

        return http.build();
    }
}
