package com.agrosatmonitor.api.service;

import com.agrosatmonitor.api.dto.relatorio.RelatorioFazendaResponse;
import com.agrosatmonitor.api.dto.soap.ConsultarRelatorioRequest;
import com.agrosatmonitor.api.dto.soap.ConsultarRelatorioResponse;
import com.agrosatmonitor.api.dto.soap.ProcessarRiscoRequest;
import com.agrosatmonitor.api.dto.soap.ProcessarRiscoResponse;
import com.agrosatmonitor.api.entity.Fazenda;
import com.agrosatmonitor.api.exception.IntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

/**
 * Serviço de relatórios que consome o AgroSat SOAP Service.
 * Demonstra integração REST → SOAP dentro da arquitetura SOA.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RelatorioService {

    private final WebServiceTemplate webServiceTemplate;
    private final FazendaService fazendaService;

    @Value("${soap.service.url}")
    private String soapUrl;

    @Value("${soap.service.namespace}")
    private String soapNamespace;

    public RelatorioFazendaResponse gerarRelatorio(Long fazendaId) {
        Fazenda fazenda = fazendaService.buscarEntidade(fazendaId);
        log.info("Gerando relatório via SOAP para fazenda '{}'", fazenda.getNome());

        try {
            // ── Operação 1: ConsultarRelatorioFazenda ─────────────────────────
            ConsultarRelatorioRequest relatorioRequest = new ConsultarRelatorioRequest();
            relatorioRequest.setFazendaId(fazendaId);

            Object relatorioRaw = webServiceTemplate.marshalSendAndReceive(
                    soapUrl,
                    relatorioRequest,
                    new SoapActionCallback(soapNamespace + "/ConsultarRelatorioFazenda")
            );

            // ── Operação 2: ProcessarRiscoAgricola ────────────────────────────
            ProcessarRiscoRequest riscoRequest = new ProcessarRiscoRequest();
            riscoRequest.setFazendaId(fazendaId);

            Object riscoRaw = webServiceTemplate.marshalSendAndReceive(
                    soapUrl,
                    riscoRequest,
                    new SoapActionCallback(soapNamespace + "/ProcessarRiscoAgricola")
            );

            // ── Monta resposta combinada ───────────────────────────────────────
            Double tempMedia = null, umidMedia = null, ndviMedio = null;
            Long qtdAlertas = null;
            String nivelRisco = "N/A", motivo = "N/A", recomendacao = "N/A";

            if (relatorioRaw instanceof ConsultarRelatorioResponse rel) {
                tempMedia   = rel.getTemperaturaMedia();
                umidMedia   = rel.getUmidadeMedia();
                ndviMedio   = rel.getNdviMedio();
                qtdAlertas  = rel.getQuantidadeAlertas();
            }

            if (riscoRaw instanceof ProcessarRiscoResponse risco) {
                nivelRisco   = risco.getNivelRisco();
                motivo       = risco.getMotivo();
                recomendacao = risco.getRecomendacao();
            }

            return new RelatorioFazendaResponse(
                    fazendaId, fazenda.getNome(),
                    tempMedia, umidMedia, ndviMedio, qtdAlertas,
                    nivelRisco, motivo, recomendacao
            );

        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Erro ao chamar SOAP Service: {}", ex.getMessage(), ex);
            throw new IntegrationException("SOAP Service", ex.getMessage(), ex);
        }
    }
}