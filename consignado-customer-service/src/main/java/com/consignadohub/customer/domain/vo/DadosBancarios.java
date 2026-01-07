package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

public record DadosBancarios(
        String banco,
        String agencia,
        String conta,
        String digito,
        TipoConta tipo
) {
    public DadosBancarios {
        if (banco == null || !banco.matches("\\d{3}")) {
            throw DomainException.invalidField("banco", "deve ser um número de 3 dígitos");
        }

        if (agencia == null || agencia.isBlank()) {
            throw DomainException.required("agencia");
        }

        if (conta == null || conta.isBlank()) {
            throw DomainException.required("conta");
        }

        if (tipo == null) {
            throw DomainException.required("tipo");
        }
    }

    public String contaCompleta() {
        return conta + (digito != null ? "-" + digito : "");
    }
}
