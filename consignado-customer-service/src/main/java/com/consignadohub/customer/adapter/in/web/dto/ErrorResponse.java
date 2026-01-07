package com.consignadohub.customer.adapter.in.web.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String code,
        String message,
        String field,
        LocalDateTime timestamp
) {
    public ErrorResponse(String code, String message) {
        this(code, message, null, LocalDateTime.now());
    }

    public ErrorResponse(String code, String message, String field) {
        this(code, message, field, LocalDateTime.now());
    }
}
