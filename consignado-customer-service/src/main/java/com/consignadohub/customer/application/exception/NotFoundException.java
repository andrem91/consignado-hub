package com.consignadohub.customer.application.exception;

import lombok.Getter;

public class NotFoundException extends RuntimeException {

    private final String resource;
    private final String identifier;

    public NotFoundException(String resource, String identifier) {
        super(String.format("%s não encontrado: %s", resource, identifier));
        this.resource = resource;
        this.identifier = identifier;
    }

    public static NotFoundException cliente(String cpf) {
        return new NotFoundException("Cliente", cpf);
    }

    public String getCode() { return "NOT_FOUND"; }
}
