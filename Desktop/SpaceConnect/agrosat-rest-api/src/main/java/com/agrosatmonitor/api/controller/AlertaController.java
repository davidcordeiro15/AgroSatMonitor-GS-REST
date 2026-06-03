package com.agrosatmonitor.api.controller;

import com.agrosatmonitor.api.dto.alerta.AlertaResponse;
import com.agrosatmonitor.api.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Geração automática de alertas agrícolas")
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping("/{fazendaId}")
    @Operation(summary = "Gera alertas automáticos com base nos últimos monitoramentos da fazenda",
               description = "Analisa temperatura, umidade, precipitação, vento e NDVI para gerar alertas de seca, temperatura extrema, chuva excessiva, vento forte e baixa vegetação.")
    public ResponseEntity<List<AlertaResponse>> gerarAlertas(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(alertaService.gerarAlertas(fazendaId));
    }
}
