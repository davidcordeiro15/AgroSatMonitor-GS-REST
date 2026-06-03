package com.agrosatmonitor.api.dto.monitoramento;

import java.time.LocalDateTime;

public record MonitoramentoClimaticoResponse(
        Long id,
        Long fazendaId,
        String nomeFazenda,
        Double latitude,
        Double longitude,
        Double temperatura,
        Double umidade,
        Double precipitacao,
        Double velocidadeVento,
        LocalDateTime dataLeitura,
        LocalDateTime dataCriacao
) {}
