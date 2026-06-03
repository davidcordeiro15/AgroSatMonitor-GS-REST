package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.client.OpenMeteoClient;
import com.agrosatmonitor.api.dto.external.OpenMeteoResponse;
import com.agrosatmonitor.api.dto.monitoramento.MonitoramentoVegetacaoResponse;
import com.agrosatmonitor.api.entity.Fazenda;
import com.agrosatmonitor.api.entity.HistoricoConsulta;
import com.agrosatmonitor.api.entity.MonitoramentoVegetacao;
import com.agrosatmonitor.api.enums.NivelSaudeVegetacao;
import com.agrosatmonitor.api.repository.HistoricoRepository;
import com.agrosatmonitor.api.repository.MonitoramentoVegetacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VegetacaoService {

    private final MonitoramentoVegetacaoRepository vegetacaoRepository;
    private final HistoricoRepository historicoRepository;
    private final OpenMeteoClient openMeteoClient;
    private final FazendaService fazendaService;

    @Transactional
    public MonitoramentoVegetacaoResponse consultarVegetacaoAtual(Long fazendaId) {
        Fazenda fazenda = fazendaService.buscarEntidade(fazendaId);
        log.info("Calculando NDVI para fazenda '{}'", fazenda.getNome());

        long inicio = System.currentTimeMillis();
        boolean sucesso = false;
        MonitoramentoVegetacao monitoramento = null;

        try {
            OpenMeteoResponse resp = openMeteoClient.buscarDadosVegetacao(
                    fazenda.getLatitude(), fazenda.getLongitude());

            double ndvi = calcularNdvi(resp, fazenda.getLatitude());
            NivelSaudeVegetacao nivel = classificarNdvi(ndvi);

            monitoramento = new MonitoramentoVegetacao();
            monitoramento.setFazenda(fazenda);
            monitoramento.setLatitude(fazenda.getLatitude());
            monitoramento.setLongitude(fazenda.getLongitude());
            monitoramento.setNdvi(Math.round(ndvi * 10000.0) / 10000.0);
            monitoramento.setNivelSaudeVegetacao(nivel);
            monitoramento.setDataLeitura(LocalDateTime.now());
            monitoramento = vegetacaoRepository.save(monitoramento);
            sucesso = true;
        } finally {
            salvarHistorico(fazenda, "GET /api/monitoramento/vegetacao/" + fazendaId,
                    System.currentTimeMillis() - inicio, sucesso);
        }

        return toResponse(monitoramento, fazenda.getNome());
    }

    public List<MonitoramentoVegetacaoResponse> historico(Long fazendaId) {
        Fazenda fazenda = fazendaService.buscarEntidade(fazendaId);
        return vegetacaoRepository.findByFazendaIdOrderByDataLeituraDesc(fazendaId)
                .stream().limit(30).map(m -> toResponse(m, fazenda.getNome())).toList();
    }

    private double calcularNdvi(OpenMeteoResponse resp, double latitude) {
        double radiacao = 15.0, precipitacao = 2.0, et0 = 3.5, umidade = 65.0;

        if (resp.getDaily() != null) {
            var daily = resp.getDaily();
            if (daily.getShortwaveRadiationSum() != null && !daily.getShortwaveRadiationSum().isEmpty())
                radiacao = daily.getShortwaveRadiationSum().get(0);
            if (daily.getPrecipitationSum() != null && !daily.getPrecipitationSum().isEmpty())
                precipitacao = daily.getPrecipitationSum().get(0);
            if (daily.getEt0FaoEvapotranspiration() != null && !daily.getEt0FaoEvapotranspiration().isEmpty())
                et0 = daily.getEt0FaoEvapotranspiration().get(0);
        }
        if (resp.getCurrent() != null && resp.getCurrent().getRelativeHumidity2m() != null)
            umidade = resp.getCurrent().getRelativeHumidity2m();

        double fatorRad  = Math.min(radiacao / 30.0, 1.0);
        double balHidrico = et0 > 0 ? Math.min(precipitacao / et0, 2.0) : 0.5;
        double fatorHid  = Math.min(balHidrico / 2.0, 1.0);
        double fatorUmid = umidade / 100.0;
        double fatorLat  = 1.0 - (Math.abs(latitude) / 90.0) * 0.3;

        double ndvi = (fatorRad * 0.3) + (fatorHid * 0.4) + (fatorUmid * 0.2) + (fatorLat * 0.1) - 0.1;
        return Math.max(-0.1, Math.min(0.9, ndvi));
    }

    private NivelSaudeVegetacao classificarNdvi(double ndvi) {
        if (ndvi < 0.1)  return NivelSaudeVegetacao.CRITICA;
        if (ndvi < 0.25) return NivelSaudeVegetacao.BAIXA;
        if (ndvi < 0.45) return NivelSaudeVegetacao.MODERADA;
        if (ndvi < 0.65) return NivelSaudeVegetacao.BOA;
        return NivelSaudeVegetacao.EXCELENTE;
    }

    private void salvarHistorico(Fazenda fazenda, String endpoint, long ms, boolean ok) {
        HistoricoConsulta h = HistoricoConsulta.builder()
                .fazenda(fazenda).endpointConsultado(endpoint).tempoRespostaMs(ms).build();
        h.setSucessoBool(ok);
        historicoRepository.save(h);
    }

    private MonitoramentoVegetacaoResponse toResponse(MonitoramentoVegetacao m, String nomeFazenda) {
        NivelSaudeVegetacao nivel = m.getNivelSaudeVegetacao();
        return new MonitoramentoVegetacaoResponse(
                m.getId(), m.getFazenda().getId(), nomeFazenda,
                m.getLatitude(), m.getLongitude(),
                m.getNdvi(),
                nivel != null ? nivel.name() : "N/A",
                interpretarNdvi(m.getNdvi()),
                m.getDataLeitura(), m.getDataCriacao());
    }

    private String interpretarNdvi(double ndvi) {
        if (ndvi < 0.0)  return "Água ou superfície sem vegetação";
        if (ndvi < 0.1)  return "Solo exposto ou vegetação crítica";
        if (ndvi < 0.25) return "Vegetação com baixo vigor — intervenção necessária";
        if (ndvi < 0.45) return "Vegetação com vigor moderado — monitorar";
        if (ndvi < 0.65) return "Vegetação saudável — condições boas";
        return "Vegetação com alto vigor — condições excelentes";
    }
}
