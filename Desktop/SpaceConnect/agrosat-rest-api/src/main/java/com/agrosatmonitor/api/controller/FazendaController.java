package com.agrosatmonitor.api.controller;

import com.agrosatmonitor.api.dto.fazenda.FazendaRequest;
import com.agrosatmonitor.api.dto.fazenda.FazendaResponse;
import com.agrosatmonitor.api.service.FazendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fazendas")
@RequiredArgsConstructor
@Tag(name = "Fazendas", description = "Gerenciamento de fazendas monitoradas")
public class FazendaController {

    private final FazendaService fazendaService;

    @GetMapping
    @Operation(summary = "Lista todas as fazendas")
    public ResponseEntity<List<FazendaResponse>> listarTodas() {
        return ResponseEntity.ok(fazendaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca fazenda por ID")
    public ResponseEntity<FazendaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fazendaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra nova fazenda")
    public ResponseEntity<FazendaResponse> criar(@Valid @RequestBody FazendaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fazendaService.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza fazenda existente")
    public ResponseEntity<FazendaResponse> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody FazendaRequest request) {
        return ResponseEntity.ok(fazendaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove fazenda")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        fazendaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
