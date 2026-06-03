package com.agrosatmonitor.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
            .info(new Info()
                .title("AgroSat REST API")
                .version("1.0.0")
                .description("""
                    API REST de Monitoramento Agrícola com dados de satélite e clima.
                    
                    **Autenticação:** Bearer Token via AuthApi (POST /auth/login).
                    
                    **Fluxo:** Cliente → AgroSat REST API → AuthApi (validação) → Oracle DB.
                    
                    Projeto acadêmico FIAP — Space Connect | ODS 2, 9, 13.
                    """)
                .contact(new Contact()
                    .name("Equipe Space Connect FIAP")
                    .email("spaceconnect@fiap.com.br"))
            )
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            .components(new Components()
                .addSecuritySchemes(securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Token JWT obtido via AuthApi POST /auth/login")
                ));
    }
}
