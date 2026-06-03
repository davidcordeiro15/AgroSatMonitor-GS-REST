package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.dto.historico.HistoricoResponse;
import com.agrosatmonitor.api.repository.HistoricoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final FazendaService fazendaService;

    public List<HistoricoResponse> listarPorFazenda(Long fazendaId) {
        fazendaService.buscarEntidade(fazendaId);
        return historicoRepository.findByFazendaIdOrderByDataConsultaDesc(fazendaId, PageRequest.of(0, 50))
                .stream().map(h -> new HistoricoResponse(
                        h.getId(), h.getFazenda().getId(),
                        h.getEndpointConsultado(), h.getDataConsulta(),
                        h.getTempoRespostaMs(), h.isSucesso()))
                .toList();
    }
}
