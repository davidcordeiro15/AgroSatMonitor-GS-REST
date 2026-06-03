package com.agrosatmonitor.api.client;

import com.agrosatmonitor.api.dto.external.OpenMeteoResponse;
import com.agrosatmonitor.api.exception.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class OpenMeteoClient {

    private final RestTemplate restTemplate;

    @Value("${openmeteo.api.url}")
    private String baseUrl;

    public OpenMeteoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OpenMeteoResponse buscarDadosClimaticos(Double latitude, Double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("current", "temperature_2m,relative_humidity_2m,wind_speed_10m,precipitation")
                .queryParam("timezone", "America/Sao_Paulo")
                .build().toUriString();

        log.info("Consultando Open-Meteo (clima): {}", url);
        return chamarApi(url);
    }

    public OpenMeteoResponse buscarDadosVegetacao(Double latitude, Double longitude) {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("daily", "shortwave_radiation_sum,precipitation_sum,et0_fao_evapotranspiration")
                .queryParam("current", "relative_humidity_2m,temperature_2m")
                .queryParam("timezone", "America/Sao_Paulo")
                .queryParam("forecast_days", "1")
                .build().toUriString();

        log.info("Consultando Open-Meteo (vegetação): {}", url);
        return chamarApi(url);
    }

    private OpenMeteoResponse chamarApi(String url) {
        try {
            OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
            if (response == null) throw new IntegrationException("Open-Meteo", "Resposta nula da API.");
            return response;
        } catch (RestClientException ex) {
            throw new IntegrationException("Open-Meteo", ex.getMessage(), ex);
        }
    }
}
