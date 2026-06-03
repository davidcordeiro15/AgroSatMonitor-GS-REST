package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProcessarRiscoResponse", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessarRiscoResponse {
    private Long fazendaId;
    private String nivelRisco;
    private Double pontuacaoRisco;
    private String motivo;
    private String recomendacao;
    private String dataAnalise;

    public Long getFazendaId() { return fazendaId; }
    public void setFazendaId(Long v) { this.fazendaId = v; }
    public String getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(String v) { this.nivelRisco = v; }
    public Double getPontuacaoRisco() { return pontuacaoRisco; }
    public void setPontuacaoRisco(Double v) { this.pontuacaoRisco = v; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String v) { this.motivo = v; }
    public String getRecomendacao() { return recomendacao; }
    public void setRecomendacao(String v) { this.recomendacao = v; }
    public String getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(String v) { this.dataAnalise = v; }
}