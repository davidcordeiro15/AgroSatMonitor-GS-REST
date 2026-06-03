package com.agrosatmonitor.api.config;

import com.agrosatmonitor.api.dto.soap.ConsultarRelatorioRequest;
import com.agrosatmonitor.api.dto.soap.ConsultarRelatorioResponse;
import com.agrosatmonitor.api.dto.soap.ProcessarRiscoRequest;
import com.agrosatmonitor.api.dto.soap.ProcessarRiscoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@Configuration
public class WebServiceClientConfig {

    @Value("${soap.service.url}")
    private String soapServiceUrl;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // setClassesToBeBound aponta para as classes locais com @XmlRootElement,
        // evitando a JAXBException de "pacote sem ObjectFactory ou jaxb.index"
        marshaller.setClassesToBeBound(
                ConsultarRelatorioRequest.class,
                ConsultarRelatorioResponse.class,
                ProcessarRiscoRequest.class,
                ProcessarRiscoResponse.class
        );
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        template.setDefaultUri(soapServiceUrl);
        return template;
    }
}