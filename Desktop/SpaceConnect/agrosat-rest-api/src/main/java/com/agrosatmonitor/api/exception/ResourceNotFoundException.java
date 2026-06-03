package com.agrosatmonitor.api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super(message); }
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " com ID " + id + " não foi encontrado(a).");
    }
}
