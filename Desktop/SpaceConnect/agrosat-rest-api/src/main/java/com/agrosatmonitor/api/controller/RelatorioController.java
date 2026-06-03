package com.agrosatmonitor.api.controller;

import com.agrosatmonitor.api.dto.relatorio.RelatorioFazendaResponse;
import com.agrosatmonitor.api.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Relatórios consolidados gerados via Web Service SOAP")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/fazenda/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Gera relatório consolidado da fazenda",
        description = """
            Consulta o AgroSat SOAP Service para obter:
            - Médias de temperatura, umidade e NDVI
            - Quantidade de alertas registrados
            - Análise de risco agrícola com recomendações
            
            **Requer role ADMIN.** Demonstra integração REST → SOAP na arquitetura SOA.
            """
    )
    public ResponseEntity<RelatorioFazendaResponse> gerarRelatorio(@PathVariable Long id) {
        return ResponseEntity.ok(relatorioService.gerarRelatorio(id));
    }
}
