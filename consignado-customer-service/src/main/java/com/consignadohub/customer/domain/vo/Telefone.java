package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

public record Telefone(String valor) {
    public Telefone {
        if (valor == null || valor.isBlank()) {
            throw DomainException.required("Telefone");
        }
        valor = valor.replaceAll("\\D", "");

        if (valor.length() < 10 || valor.length() > 11) {
            throw DomainException.invalidField("Telefone", "deve ter entre 10 e 11 dígitos");
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
