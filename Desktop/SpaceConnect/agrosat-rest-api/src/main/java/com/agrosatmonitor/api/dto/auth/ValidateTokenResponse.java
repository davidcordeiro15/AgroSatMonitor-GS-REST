package com.agrosatmonitor.api.dto.auth;

public record ValidateTokenResponse(boolean valid, String email, String role) {}
