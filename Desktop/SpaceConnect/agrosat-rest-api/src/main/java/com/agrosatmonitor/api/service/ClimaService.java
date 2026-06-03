package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.client.OpenMeteoClient;
import com.agrosatmonitor.api.dto.external.OpenMeteoResponse;
import com.agrosatmonitor.api.dto.monitoramento.MonitoramentoClimaticoResponse;
import com.agrosatmonitor.api.entity.Fazenda;
import com.agrosatmonitor.api.entity.HistoricoConsulta;
import com.agrosatmonitor.api.entity.MonitoramentoClimatico;
import com.agrosatmonitor.api.repository.HistoricoRepository;
import com.agrosatmonitor.api.repository.MonitoramentoClimaticoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClimaService {

    private final MonitoramentoClimaticoRepository climaticoRepository;
    private final HistoricoRepository historicoRepository;
    private final OpenMeteoClient openMeteoClient;
    private final FazendaService fazendaService;

    @Transactional
    public MonitoramentoClimaticoResponse consultarClimaAtual(Long fazendaId) {
        Fazenda fazenda = fazendaService.buscarEntidade(fazendaId);
        log.info("Consultando clima para fazenda '{}' ({}, {})",
                fazenda.getNome(), fazenda.getLatitude(), fazenda.getLongitude());

        long inicio = System.currentTimeMillis();
        boolean sucesso = false;
        MonitoramentoClimatico monitoramento = null;

        try {
            OpenMeteoResponse resp = openMeteoClient.buscarDadosClimaticos(
                    fazenda.getLatitude(), fazenda.getLongitude());

            OpenMeteoResponse.CurrentData current = resp.getCurrent();
            monitoramento = new MonitoramentoClimatico();
            monitoramento.setFazenda(fazenda);
            monitoramento.setLatitude(fazenda.getLatitude());
            monitoramento.setLongitude(fazenda.getLongitude());
            monitoramento.setTemperatura(current.getTemperature2m());
            monitoramento.setUmidade(current.getRelativeHumidity2m());
            monitoramento.setPrecipitacao(current.getPrecipitation() != null ? current.getPrecipitation() : 0.0);
            monitoramento.setVelocidadeVento(current.getWindSpeed10m());
            monitoramento.setDataLeitura(LocalDateTime.now());
            monitoramento = climaticoRepository.save(monitoramento);
            sucesso = true;
        } finally {
            salvarHistorico(fazenda, "GET /api/monitoramento/clima/" + fazendaId,
                    System.currentTimeMillis() - inicio, sucesso);
        }

        return toResponse(monitoramento, fazenda.getNome());
    }

    public List<MonitoramentoClimaticoResponse> historico(Long fazendaId) {
        Fazenda fazenda = fazendaService.buscarEntidade(fazendaId);
        return climaticoRepository.findByFazendaIdOrderByDataLeituraDesc(fazendaId)
                .stream().limit(30).map(m -> toResponse(m, fazenda.getNome())).toList();
    }

    private void salvarHistorico(Fazenda fazenda, String endpoint, long ms, boolean ok) {
        HistoricoConsulta h = HistoricoConsulta.builder()
                .fazenda(fazenda).endpointConsultado(endpoint)
                .tempoRespostaMs(ms).build();
        h.setSucessoBool(ok);
        historicoRepository.save(h);
    }

    private MonitoramentoClimaticoResponse toResponse(MonitoramentoClimatico m, String nomeFazenda) {
        return new MonitoramentoClimaticoResponse(
                m.getId(), m.getFazenda().getId(), nomeFazenda,
                m.getLatitude(), m.getLongitude(),
                m.getTemperatura(), m.getUmidade(), m.getPrecipitacao(),
                m.getVelocidadeVento(), m.getDataLeitura(), m.getDataCriacao());
    }
}
