package com.agrosatmonitor.api.dto.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProcessarRiscoRequest", namespace = "http://agrosatmonitor.com/soap")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessarRiscoRequest {
    private Long fazendaId;

    public Long getFazendaId() { return fazendaId; }
    public void setFazendaId(Long fazendaId) { this.fazendaId = fazendaId; }
}