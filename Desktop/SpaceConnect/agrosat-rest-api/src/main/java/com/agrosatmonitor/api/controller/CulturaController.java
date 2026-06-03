package com.agrosatmonitor.api.controller;

import com.agrosatmonitor.api.dto.cultura.CulturaRequest;
import com.agrosatmonitor.api.dto.cultura.CulturaResponse;
import com.agrosatmonitor.api.service.CulturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/culturas")
@RequiredArgsConstructor
@Tag(name = "Culturas", description = "Gerenciamento de culturas agrícolas")
public class CulturaController {

    private final CulturaService culturaService;

    @GetMapping
    @Operation(summary = "Lista todas as culturas")
    public ResponseEntity<List<CulturaResponse>> listarTodas() {
        return ResponseEntity.ok(culturaService.listarTodas());
    }

    @GetMapping("/fazenda/{fazendaId}")
    @Operation(summary = "Lista culturas de uma fazenda")
    public ResponseEntity<List<CulturaResponse>> listarPorFazenda(@PathVariable Long fazendaId) {
        return ResponseEntity.ok(culturaService.listarPorFazenda(fazendaId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca cultura por ID")
    public ResponseEntity<CulturaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(culturaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra nova cultura")
    public ResponseEntity<CulturaResponse> criar(@Valid @RequestBody CulturaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(culturaService.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza cultura")
    public ResponseEntity<CulturaResponse> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody CulturaRequest request) {
        return ResponseEntity.ok(culturaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove cultura")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        culturaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
