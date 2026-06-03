package com.agrosatmonitor.api.dto.cultura;

public record CulturaResponse(
        Long id,
        String nome,
        String tipo,
        String safra,
        Long fazendaId,
        String nomeFazenda
) {}
