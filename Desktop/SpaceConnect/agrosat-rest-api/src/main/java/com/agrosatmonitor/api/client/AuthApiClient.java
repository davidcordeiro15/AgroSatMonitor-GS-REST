package com.agrosatmonitor.api.client;

import com.agrosatmonitor.api.dto.auth.ValidateTokenRequest;
import com.agrosatmonitor.api.dto.auth.ValidateTokenResponse;
import com.agrosatmonitor.api.exception.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AuthApiClient {

    private final RestTemplate restTemplate;

    @Value("${auth.api.url}")
    private String authApiUrl;

    @Value("${auth.api.validate-endpoint}")
    private String validateEndpoint;

    public AuthApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Valida o token JWT delegando à AuthApi.
     * Implementa o contrato de serviço definido pelo endpoint POST /auth/validate.
     */
    public ValidateTokenResponse validate(String token) {
        String url = authApiUrl + validateEndpoint;
        log.debug("Validando token na AuthApi: {}", url);

        try {
            ResponseEntity<ValidateTokenResponse> response = restTemplate.postForEntity(
                    url,
                    new ValidateTokenRequest(token),
                    ValidateTokenResponse.class
            );

            if (response.getBody() == null) {
                return new ValidateTokenResponse(false, null, null);
            }

            return response.getBody();

        } catch (RestClientException ex) {
            log.error("Falha ao contactar AuthApi: {}", ex.getMessage());
            throw new IntegrationException("AuthApi", "Serviço de autenticação indisponível.", ex);
        }
    }
}
