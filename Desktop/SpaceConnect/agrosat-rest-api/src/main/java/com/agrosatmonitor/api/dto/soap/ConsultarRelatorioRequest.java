package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "ConsultarRelatorioRequest", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultarRelatorioRequest {

    @XmlElement(name = "fazendaId", namespace = "http://agrosatmonitor.com/soap")
    private Long fazendaId;

    @XmlElement(name = "dataInicio", namespace = "http://agrosatmonitor.com/soap")
    private String dataInicio;

    @XmlElement(name = "dataFim", namespace = "http://agrosatmonitor.com/soap")
    private String dataFim;

    public Long getFazendaId() { return fazendaId; }
    public void setFazendaId(Long fazendaId) { this.fazendaId = fazendaId; }
    public String getDataInicio() { return dataInicio; }
    public void setDataInicio(String dataInicio) { this.dataInicio = dataInicio; }
    public String getDataFim() { return dataFim; }
    public void setDataFim(String dataFim) { this.dataFim = dataFim; }
}