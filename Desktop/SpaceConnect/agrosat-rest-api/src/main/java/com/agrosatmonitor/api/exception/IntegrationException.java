package com.agrosatmonitor.api.exception;

public class IntegrationException extends RuntimeException {
    private final String service;
    public IntegrationException(String service, String message) {
        super("Erro de integração com '" + service + "': " + message);
        this.service = service;
    }
    public IntegrationException(String service, String message, Throwable cause) {
        super("Erro de integração com '" + service + "': " + message, cause);
        this.service = service;
    }
    public String getService() { return service; }
}
