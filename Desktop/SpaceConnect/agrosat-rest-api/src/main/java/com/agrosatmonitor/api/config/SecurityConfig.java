package com.agrosatmonitor.api.config;

import com.agrosatmonitor.api.security.AuthApiFilter;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthApiFilter authApiFilter;

    public SecurityConfig(AuthApiFilter authApiFilter) {
        this.authApiFilter = authApiFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exc ->
                exc.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .authorizeHttpRequests(auth -> auth
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/error",
                    "/ws/**"
                ).permitAll()
                .requestMatchers("/api/fazendas/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/culturas/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/monitoramento/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/alertas/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/relatorios/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(authApiFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
