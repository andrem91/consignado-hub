package com.consignadohub.simulation.application.port.out;

import com.consignadohub.simulation.domain.model.Simulacao;

import java.util.Optional;

/**
 * Port Out - Define o contrato para cache de simulações.
 * Implementado pelo adapter Redis.
 */
public interface SimulacaoCache {

    /**
     * Busca simulação pela assinatura (cache inteligente).
     * Assinatura: "V10000.00:P24:T1.66"
     */
    Optional<Simulacao> buscarPorAssinatura(String assinatura);

    /**
     * Salva simulação no cache com TTL.
     */
    void salvar(Simulacao simulacao, String assinatura);
}
