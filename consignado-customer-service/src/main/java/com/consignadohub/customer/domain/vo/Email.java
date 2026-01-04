package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidEmailException;

public record Email(String valor) {

    public Email {
        if (valor == null || valor.isEmpty()) {
            throw new InvalidEmailException("Email não pode ser nulo ou vazio");
        }
        valor = valor.toLowerCase();

        if (!valor.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidEmailException("Email inválido");
        }
    }
}
