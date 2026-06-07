package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "ProcessarRiscoRequest", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessarRiscoRequest {

    @XmlElement(name = "fazendaId", namespace = "http://agrosatmonitor.com/soap")
    private Long fazendaId;

    public Long getFazendaId() { return fazendaId; }
    public void setFazendaId(Long fazendaId) { this.fazendaId = fazendaId; }
}