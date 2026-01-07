package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

public record NumeroBeneficio(String valor) {

    public NumeroBeneficio {
        if (valor == null || valor.isBlank()) {
            throw DomainException.required("NumeroBeneficio");
        }

        valor = valor.replaceAll("[^0-9]", "");

        if (valor.length() != 10) {
            throw DomainException.invalidField("NumeroBeneficio", "deve ter 10 dígitos");
        }

    }

    public String formatar() {
        return valor.substring(0, 3) + "." +
                valor.substring(3, 6) + "." +
                valor.substring(6, 9) + "-" +
                valor.substring(9, 10);
    }
}
