package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

/**
 * Value Object que representa um endereço de e-mail válido.
 * Valida formato com regex e normaliza para lowercase.
 */
public record Email(String valor) {

    public Email {
        if (valor == null || valor.isEmpty()) {
            throw DomainException.required("email");
        }
        valor = valor.toLowerCase();

        if (!valor.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw DomainException.invalidField("email", "deve ser um endereço de e-mail válido");
        }
    }
}
