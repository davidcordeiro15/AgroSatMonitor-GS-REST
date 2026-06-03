package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ConsultarRelatorioRequest", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarRelatorioRequest {
    private Long fazendaId;
    private String dataInicio;
    private String dataFim;

    public Long getFazendaId() { return fazendaId; }
    public void setFazendaId(Long fazendaId) { this.fazendaId = fazendaId; }
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
}