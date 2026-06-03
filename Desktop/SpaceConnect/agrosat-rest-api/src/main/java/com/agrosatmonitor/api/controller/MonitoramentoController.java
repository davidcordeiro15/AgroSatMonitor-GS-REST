package com.agrosatmonitor.api.controller;

import com.agrosatmonitor.api.dto.historico.HistoricoResponse;
import com.agrosatmonitor.api.dto.monitoramento.MonitoramentoClimaticoResponse;
import com.agrosatmonitor.api.dto.monitoramento.MonitoramentoVegetacaoResponse;
import com.agrosatmonitor.api.service.ClimaService;
import com.agrosatmonitor.api.service.HistoricoService;
import com.agrosatmonitor.api.service.VegetacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitoramento")
@RequiredArgsConstructor
@Tag(name = "Monitoramento", description = "Dados climáticos e de vegetação via Open-Meteo")
public class MonitoramentoController {

    private final ClimaService climaService;
    private final VegetacaoService vegetacaoService;
    private final HistoricoService historicoService;

    @GetMapping("/clima/{fazendaId}")
    @Operation(summary = "Consulta clima atual da fazenda via Open-Meteo e persiste o resultado")
    public ResponseEntity<MonitoramentoClimaticoResponse> consultarClima(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(climaService.consultarClimaAtual(fazendaId));
    }

    @GetMapping("/clima/{fazendaId}/historico")
    @Operation(summary = "Últimos 30 registros climáticos da fazenda")
    public ResponseEntity<List<MonitoramentoClimaticoResponse>> historicoClima(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(climaService.historico(fazendaId));
    }

    @GetMapping("/vegetacao/{fazendaId}")
    @Operation(summary = "Calcula NDVI da fazenda e persiste o resultado")
    public ResponseEntity<MonitoramentoVegetacaoResponse> consultarVegetacao(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(vegetacaoService.consultarVegetacaoAtual(fazendaId));
    }

    @GetMapping("/vegetacao/{fazendaId}/historico")
    @Operation(summary = "Últimos 30 registros de vegetação da fazenda")
    public ResponseEntity<List<MonitoramentoVegetacaoResponse>> historicoVegetacao(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(vegetacaoService.historico(fazendaId));
    }

    @GetMapping("/historico/{fazendaId}")
    @Operation(summary = "Log das últimas 50 consultas externas da fazenda")
    public ResponseEntity<List<HistoricoResponse>> historicoConsultas(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(historicoService.listarPorFazenda(fazendaId));
    }
}
