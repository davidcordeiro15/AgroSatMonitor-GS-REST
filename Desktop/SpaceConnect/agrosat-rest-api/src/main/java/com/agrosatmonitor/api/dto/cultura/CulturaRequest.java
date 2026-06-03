package com.agrosatmonitor.api.dto.cultura;

import jakarta.validation.constraints.*;

public record CulturaRequest(
        @NotBlank(message = "Nome da cultura é obrigatório")
        @Size(max = 100)
        String nome,

        @NotBlank(message = "Tipo é obrigatório")
        @Size(max = 100)
        String tipo,

        @NotBlank(message = "Safra é obrigatória")
        @Size(max = 20)
        String safra,

        @NotNull(message = "ID da fazenda é obrigatório")
        Long fazendaId
) {}
