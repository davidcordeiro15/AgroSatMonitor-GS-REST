package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConsultarRelatorioResponse", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarRelatorioResponse {
    @XmlElement(name = "fazendaId",         namespace = "http://agrosatmonitor.com/soap")
    private Long fazendaId;
    @XmlElement(name = "nomeFazenda",        namespace = "http://agrosatmonitor.com/soap")
    private String nomeFazenda;
    @XmlElement(name = "temperaturaMedia",   namespace = "http://agrosatmonitor.com/soap")
    private Double temperaturaMedia;
    @XmlElement(name = "umidadeMedia",       namespace = "http://agrosatmonitor.com/soap")
    private Double umidadeMedia;
    @XmlElement(name = "precipitacaoTotal",  namespace = "http://agrosatmonitor.com/soap")
    private Double precipitacaoTotal;
    @XmlElement(name = "ndviMedio",          namespace = "http://agrosatmonitor.com/soap")
    private Double ndviMedio;
    @XmlElement(name = "quantidadeAlertas",  namespace = "http://agrosatmonitor.com/soap")
    private Long quantidadeAlertas;
    @XmlElement(name = "periodoInicio",      namespace = "http://agrosatmonitor.com/soap")
    private String periodoInicio;
    @XmlElement(name = "periodoFim",         namespace = "http://agrosatmonitor.com/soap")
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