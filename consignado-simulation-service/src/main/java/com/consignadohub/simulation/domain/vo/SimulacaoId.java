package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;

import java.util.UUID;

public class SimulacaoId {

    private final UUID valor;

    private SimulacaoId(UUID valor) {
        if (valor == null) {
            throw DomainException.required("SimulacaoId");
        }
        this.valor = valor;
    }

    public static SimulacaoId gerar() {
        return new SimulacaoId(UUID.randomUUID());
    }

    public static SimulacaoId of(UUID valor) {
        return new SimulacaoId(valor);
    }

    public UUID valor() {
        return valor;
    }
}
