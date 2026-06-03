package com.agrosatmonitor.api.dto.alerta;

import java.time.LocalDateTime;

public record AlertaResponse(
        Long id,
        Long fazendaId,
        String nomeFazenda,
        String tipo,
        String descricao,
        String nivelRisco,
        LocalDateTime dataGeracao
) {}
