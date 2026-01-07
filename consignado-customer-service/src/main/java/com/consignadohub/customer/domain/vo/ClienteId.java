package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

import java.util.UUID;

public record ClienteId(UUID valor) {

    public ClienteId {
        if (valor == null) {
            throw DomainException.required("id");
        }
    }

    public static ClienteId novo() {
        return new ClienteId(UUID.randomUUID());
    }

    public static ClienteId of(UUID valor) {
        return new ClienteId(valor);
    }
}
