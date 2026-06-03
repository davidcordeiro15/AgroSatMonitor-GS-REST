package com.agrosatmonitor.api.dto.historico;

import java.time.LocalDateTime;

public record HistoricoResponse(
        Long id,
        Long fazendaId,
        String endpointConsultado,
        LocalDateTime dataConsulta,
        Long tempoRespostaMs,
        boolean sucesso
) {}
