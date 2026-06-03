package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.dto.cultura.CulturaRequest;
import com.agrosatmonitor.api.dto.cultura.CulturaResponse;
import com.agrosatmonitor.api.entity.CulturaAgricola;
import com.agrosatmonitor.api.entity.Fazenda;
import com.agrosatmonitor.api.exception.ResourceNotFoundException;
import com.agrosatmonitor.api.repository.CulturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CulturaService {

    private final CulturaRepository culturaRepository;
    private final FazendaService fazendaService;

    public List<CulturaResponse> listarTodas() {
        return culturaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public List<CulturaResponse> listarPorFazenda(Long fazendaId) {
        fazendaService.buscarEntidade(fazendaId); // valida existência
        return culturaRepository.findByFazendaId(fazendaId).stream().map(this::toResponse).toList();
    }

    public CulturaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public CulturaResponse criar(CulturaRequest request) {
        Fazenda fazenda = fazendaService.buscarEntidade(request.fazendaId());
        CulturaAgricola cultura = CulturaAgricola.builder()
                .nome(request.nome())
                .tipo(request.tipo())
                .safra(request.safra())
                .fazenda(fazenda)
                .build();
        return toResponse(culturaRepository.save(cultura));
    }

    @Transactional
    public CulturaResponse atualizar(Long id, CulturaRequest request) {
        CulturaAgricola cultura = buscarEntidade(id);
        Fazenda fazenda = fazendaService.buscarEntidade(request.fazendaId());
        cultura.setNome(request.nome());
        cultura.setTipo(request.tipo());
        cultura.setSafra(request.safra());
        cultura.setFazenda(fazenda);
        return toResponse(culturaRepository.save(cultura));
    }

    @Transactional
    public void excluir(Long id) {
        if (!culturaRepository.existsById(id))
            throw new ResourceNotFoundException("Cultura", id);
        culturaRepository.deleteById(id);
    }

    private CulturaAgricola buscarEntidade(Long id) {
        return culturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cultura", id));
    }

    private CulturaResponse toResponse(CulturaAgricola c) {
        return new CulturaResponse(c.getId(), c.getNome(), c.getTipo(), c.getSafra(),
                c.getFazenda().getId(), c.getFazenda().getNome());
    }
}
