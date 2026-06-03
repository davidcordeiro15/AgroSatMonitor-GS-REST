package com.agrosatmonitor.api.dto.fazenda;

import java.time.LocalDateTime;

public record FazendaResponse(
        Long id,
        String nome,
        Double latitude,
        Double longitude,
        Double areaHectares,
        String cidade,
        String estado,
        LocalDateTime dataCadastro
) {}
