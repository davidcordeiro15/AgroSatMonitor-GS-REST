package com.agrosatmonitor.api.security;

import com.agrosatmonitor.api.client.AuthApiClient;
import com.agrosatmonitor.api.dto.auth.ValidateTokenResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de autenticação que delega a validação do JWT para a AuthApi.
 * Implementa o princípio SOA de reutilização de serviços.
 * NÃO valida o token localmente — toda autenticação passa pela AuthApi.
 */
@Component
@Slf4j
public class AuthApiFilter extends OncePerRequestFilter {

    private final AuthApiClient authApiClient;

    public AuthApiFilter(AuthApiClient authApiClient) {
        this.authApiClient = authApiClient;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/swagger-ui") ||
               path.startsWith("/v3/api-docs") ||
               path.startsWith("/error") ||
               path.startsWith("/ws");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);

                ValidateTokenResponse validation = authApiClient.validate(token);

                if (validation.valid()) {
                    log.debug("Token válido para: {} | role: {}", validation.email(), validation.role());

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            validation.email(),
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + validation.role()))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    log.warn("Token inválido rejeitado pela AuthApi.");
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (Exception ex) {
            log.error("Erro no filtro de autenticação: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
