package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.dto.alerta.AlertaResponse;
import com.agrosatmonitor.api.entity.AlertaAgricola;
import com.agrosatmonitor.api.entity.Fazenda;
import com.agrosatmonitor.api.entity.MonitoramentoClimatico;
import com.agrosatmonitor.api.entity.MonitoramentoVegetacao;
import com.agrosatmonitor.api.enums.NivelRisco;
import com.agrosatmonitor.api.enums.TipoAlerta;
import com.agrosatmonitor.api.repository.AlertaRepository;
import com.agrosatmonitor.api.repository.MonitoramentoClimaticoRepository;
import com.agrosatmonitor.api.repository.MonitoramentoVegetacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final MonitoramentoClimaticoRepository climaticoRepository;
    private final MonitoramentoVegetacaoRepository vegetacaoRepository;
    private final FazendaService fazendaService;

    @Transactional
    public List<AlertaResponse> gerarAlertas(Long fazendaId) {
        Fazenda fazenda = fazendaService.buscarEntidade(fazendaId);
        log.info("Gerando alertas para fazenda '{}'", fazenda.getNome());

        List<AlertaAgricola> novosAlertas = new ArrayList<>();

        climaticoRepository.findFirstByFazendaIdOrderByDataLeituraDesc(fazendaId)
                .ifPresent(clima -> novosAlertas.addAll(verificarAlertasClimaticos(fazenda, clima)));

        vegetacaoRepository.findFirstByFazendaIdOrderByDataLeituraDesc(fazendaId)
                .ifPresent(veg -> novosAlertas.addAll(verificarAlertasVegetacao(fazenda, veg)));

        if (!novosAlertas.isEmpty()) {
            alertaRepository.saveAll(novosAlertas);
            return novosAlertas.stream().map(a -> toResponse(a, fazenda.getNome())).toList();
        }

        return alertaRepository.findByFazendaIdOrderByDataGeracaoDesc(fazendaId)
                .stream().limit(20).map(a -> toResponse(a, fazenda.getNome())).toList();
    }

    private List<AlertaAgricola> verificarAlertasClimaticos(Fazenda fazenda, MonitoramentoClimatico clima) {
        List<AlertaAgricola> alertas = new ArrayList<>();

        if (clima.getTemperatura() > 38) {
            AlertaAgricola a = new AlertaAgricola();
            a.setFazenda(fazenda);
            a.setTipoAlerta(TipoAlerta.TEMPERATURA_EXTREMA);
            a.setDescricao(String.format(
                    "Temperatura muito alta: %.1f°C. Risco de estresse térmico nas culturas. Irrigação emergencial recomendada.",
                    clima.getTemperatura()));
            a.setNivelRisco(clima.getTemperatura() > 42 ? NivelRisco.CRITICO : NivelRisco.ALTO);
            alertas.add(a);
        } else if (clima.getTemperatura() < 5) {
            AlertaAgricola a = new AlertaAgricola();
            a.setFazenda(fazenda);
            a.setTipoAlerta(TipoAlerta.TEMPERATURA_EXTREMA);
            a.setDescricao(String.format(
                    "Temperatura muito baixa: %.1f°C. Risco de geada. Proteja as culturas sensíveis.",
                    clima.getTemperatura()));
            a.setNivelRisco(clima.getTemperatura() < 0 ? NivelRisco.CRITICO : NivelRisco.ALTO);
            alertas.add(a);
        }

        if (clima.getPrecipitacao() == 0 && clima.getUmidade() < 30) {
            AlertaAgricola a = new AlertaAgricola();
            a.setFazenda(fazenda);
            a.setTipoAlerta(TipoAlerta.SECA);
            a.setDescricao(String.format(
                    "Condições de seca: precipitação %.1fmm, umidade %.1f%%. Acione irrigação.",
                    clima.getPrecipitacao(), clima.getUmidade()));
            a.setNivelRisco(clima.getUmidade() < 15 ? NivelRisco.CRITICO : NivelRisco.ALTO);
            alertas.add(a);
        }

        if (clima.getPrecipitacao() > 50) {
            AlertaAgricola a = new AlertaAgricola();
            a.setFazenda(fazenda);
            a.setTipoAlerta(TipoAlerta.CHUVA_EXCESSIVA);
            a.setDescricao(String.format(
                    "Precipitação excessiva: %.1fmm. Risco de encharcamento e doenças fúngicas.",
                    clima.getPrecipitacao()));
            a.setNivelRisco(clima.getPrecipitacao() > 100 ? NivelRisco.CRITICO : NivelRisco.ALTO);
            alertas.add(a);
        }

        if (clima.getVelocidadeVento() > 60) {
            AlertaAgricola a = new AlertaAgricola();
            a.setFazenda(fazenda);
            a.setTipoAlerta(TipoAlerta.VENTO_FORTE);
            a.setDescricao(String.format(
                    "Vento elevado: %.1f km/h. Suspend pulverizações. Risco de danos mecânicos.",
                    clima.getVelocidadeVento()));
            a.setNivelRisco(clima.getVelocidadeVento() > 80 ? NivelRisco.CRITICO : NivelRisco.MEDIO);
            alertas.add(a);
        }

        return alertas;
    }

    private List<AlertaAgricola> verificarAlertasVegetacao(Fazenda fazenda, MonitoramentoVegetacao veg) {
        List<AlertaAgricola> alertas = new ArrayList<>();
        if (veg.getNdvi() < 0.25) {
            AlertaAgricola a = new AlertaAgricola();
            a.setFazenda(fazenda);
            a.setTipoAlerta(TipoAlerta.BAIXA_VEGETACAO);
            a.setDescricao(String.format(
                    "NDVI baixo: %.4f. Vegetação com baixo vigor ou solo exposto. Verifique nutrientes e água.",
                    veg.getNdvi()));
            a.setNivelRisco(veg.getNdvi() < 0.1 ? NivelRisco.CRITICO : NivelRisco.ALTO);
            alertas.add(a);
        }
        return alertas;
    }

    private AlertaResponse toResponse(AlertaAgricola a, String nomeFazenda) {
        return new AlertaResponse(
                a.getId(), a.getFazenda().getId(), nomeFazenda,
                a.getTipoAlerta() != null ? a.getTipoAlerta().name() : "N/A",
                a.getDescricao(),
                a.getNivelRisco() != null ? a.getNivelRisco().name() : "N/A",
                a.getDataGeracao());
    }
}
