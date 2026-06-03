package com.agrosatmonitor.api.dto.relatorio;

public record RelatorioFazendaResponse(
        Long fazendaId,
        String nomeFazenda,
        Double temperaturaMedia,
        Double umidadeMedia,
        Double ndviMedio,
        Long quantidadeAlertas,
        String nivelRisco,
        String motivo,
        String recomendacao
) {}
