package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidTelefoneException;

public record Telefone(String valor) {
    public Telefone {
        if (valor == null || valor.isBlank()) {
            throw new InvalidTelefoneException("Telefone não pode ser nulo");
        }
        valor = valor.replaceAll("\\D", "");

        if (valor.length() < 10 || valor.length() > 11) {
            throw new InvalidTelefoneException("Telefone deve ter 10 ou 11 dígitos");
        }
    }

    public String formatar() {
        if (valor.length() == 11) {
            return "(" + valor.substring(0, 2) + ") " +
                    valor.substring(2, 7) + "-" +
                    valor.substring(7);
        }
        return "(" + valor.substring(0, 2) + ") " +
                valor.substring(2, 6) + "-" +
                valor.substring(6);
    }
}
