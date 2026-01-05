package com.consignadohub.customer.domain.vo;

import java.util.UUID;

public record ClienteId(UUID valor) {

    public ClienteId {
        if (valor == null) {
            throw new IllegalArgumentException("ClientId não pode ser nulo");
        }
    }

    public static ClienteId novo() {
        return new ClienteId(UUID.randomUUID());
    }

    public static ClienteId of(UUID valor) {
        return new ClienteId(valor);
    }
}
