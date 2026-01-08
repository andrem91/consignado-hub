package com.consignadohub.simulation.domain.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final String code;
    private final String field;

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
        this.field = null;
    }

    public DomainException(String code, String field, String message) {
        super(message);
        this.code = code;
        this.field = field;
    }

    public static DomainException invalidField(String field, String reason) {
        return new DomainException(
                "INVALID_" + field.toUpperCase(),
                field,
                String.format("%s: %s", field, reason)
        );
    }

    public static DomainException required(String field) {
        return new DomainException(
            "REQUIRED_FIELD",
                field,
                String.format("%s é obrigatório", field)
        );
    }
}
