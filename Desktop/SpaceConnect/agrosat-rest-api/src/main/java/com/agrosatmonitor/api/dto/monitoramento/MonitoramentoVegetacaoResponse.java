package com.agrosatmonitor.api.dto.monitoramento;

import java.time.LocalDateTime;

public record MonitoramentoVegetacaoResponse(
        Long id,
        Long fazendaId,
        String nomeFazenda,
        Double latitude,
        Double longitude,
        Double ndvi,
        String nivelSaudeVegetacao,
        String interpretacaoNdvi,
        LocalDateTime dataLeitura,
        LocalDateTime dataCriacao
) {}
