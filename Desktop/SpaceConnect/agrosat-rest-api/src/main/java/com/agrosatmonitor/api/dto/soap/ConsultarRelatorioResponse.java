package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConsultarRelatorioResponse", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarRelatorioResponse {
    private Long fazendaId;
    private String nomeFazenda;
    private Double temperaturaMedia;
    private Double umidadeMedia;
    private Double precipitacaoTotal;
    private Double ndviMedio;
    private Long quantidadeAlertas;
    private String periodoInicio;
    private String periodoFim;

    public Long getFazendaId() { return fazendaId; }
    public void setFazendaId(Long v) { this.fazendaId = v; }
    public String getNomeFazenda() { return nomeFazenda; }
    public void setNomeFazenda(String v) { this.nomeFazenda = v; }
    public Double getTemperaturaMedia() { return temperaturaMedia; }
    public void setTemperaturaMedia(Double v) { this.temperaturaMedia = v; }
    public Double getUmidadeMedia() { return umidadeMedia; }
    public void setUmidadeMedia(Double v) { this.umidadeMedia = v; }
    public Double getPrecipitacaoTotal() { return precipitacaoTotal; }
    public void setPrecipitacaoTotal(Double v) { this.precipitacaoTotal = v; }
    public Double getNdviMedio() { return ndviMedio; }
    public void setNdviMedio(Double v) { this.ndviMedio = v; }
    public Long getQuantidadeAlertas() { return quantidadeAlertas; }
    public void setQuantidadeAlertas(Long v) { this.quantidadeAlertas = v; }
    public String getPeriodoInicio() { return periodoInicio; }
    public void setPeriodoInicio(String v) { this.periodoInicio = v; }
    public String getPeriodoFim() { return periodoFim; }
    public void setPeriodoFim(String v) { this.periodoFim = v; }
}