package com.agrosatmonitor.api.dto.fazenda;

import jakarta.validation.constraints.*;

public record FazendaRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 200, message = "Nome deve ter no máximo 200 caracteres")
        String nome,

        @NotNull(message = "Latitude é obrigatória")
        @DecimalMin(value = "-90.0", message = "Latitude mínima: -90")
        @DecimalMax(value = "90.0", message = "Latitude máxima: 90")
        Double latitude,

        @NotNull(message = "Longitude é obrigatória")
        @DecimalMin(value = "-180.0", message = "Longitude mínima: -180")
        @DecimalMax(value = "180.0", message = "Longitude máxima: 180")
        Double longitude,

        @Positive(message = "Área deve ser positiva")
        Double areaHectares,

        @NotBlank(message = "Cidade é obrigatória")
        @Size(max = 100)
        String cidade,

        @NotBlank(message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Use a sigla do estado (ex: SP)")
        String estado
) {}
