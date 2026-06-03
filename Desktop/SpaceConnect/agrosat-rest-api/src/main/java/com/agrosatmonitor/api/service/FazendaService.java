package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.dto.fazenda.FazendaRequest;
import com.agrosatmonitor.api.dto.fazenda.FazendaResponse;
import com.agrosatmonitor.api.entity.Fazenda;
import com.agrosatmonitor.api.exception.ResourceNotFoundException;
import com.agrosatmonitor.api.repository.FazendaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FazendaService {

    private final FazendaRepository fazendaRepository;

    public List<FazendaResponse> listarTodas() {
        return fazendaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public FazendaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public FazendaResponse criar(FazendaRequest request) {
        log.info("Criando fazenda: {}", request.nome());
        Fazenda fazenda = Fazenda.builder()
                .nome(request.nome())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .areaHectares(request.areaHectares())
                .cidade(request.cidade())
                .estado(request.estado().toUpperCase())
                .build();
        return toResponse(fazendaRepository.save(fazenda));
    }

    @Transactional
    public FazendaResponse atualizar(Long id, FazendaRequest request) {
        Fazenda fazenda = buscarEntidade(id);
        fazenda.setNome(request.nome());
        fazenda.setLatitude(request.latitude());
        fazenda.setLongitude(request.longitude());
        fazenda.setAreaHectares(request.areaHectares());
        fazenda.setCidade(request.cidade());
        fazenda.setEstado(request.estado().toUpperCase());
        return toResponse(fazendaRepository.save(fazenda));
    }

    @Transactional
    public void excluir(Long id) {
        if (!fazendaRepository.existsById(id))
            throw new ResourceNotFoundException("Fazenda", id);
        fazendaRepository.deleteById(id);
        log.info("Fazenda ID={} excluída", id);
    }

    public Fazenda buscarEntidade(Long id) {
        return fazendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fazenda", id));
    }

    private FazendaResponse toResponse(Fazenda f) {
        return new FazendaResponse(f.getId(), f.getNome(), f.getLatitude(), f.getLongitude(),
                f.getAreaHectares(), f.getCidade(), f.getEstado(), f.getDataCadastro());
    }
}
