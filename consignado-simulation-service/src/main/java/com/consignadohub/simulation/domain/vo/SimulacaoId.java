package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;

import java.util.UUID;

public record SimulacaoId(UUID valor) {

    public SimulacaoId {
        if (valor == null) {
            throw DomainException.required("SimulacaoId");
        }
    }

    public static SimulacaoId gerar() {
        return new SimulacaoId(UUID.randomUUID());
    }

    public static SimulacaoId of(UUID valor) {
        return new SimulacaoId(valor);
    }

}
