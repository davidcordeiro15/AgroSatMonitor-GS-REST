package com.agrosatmonitor.api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String erro,
        String mensagem,
        String path,
        LocalDateTime timestamp
) {}
