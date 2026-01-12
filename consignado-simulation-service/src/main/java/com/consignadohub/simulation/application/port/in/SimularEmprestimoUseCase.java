package com.consignadohub.simulation.application.port.in;

import com.consignadohub.simulation.domain.model.Simulacao;

/**
 * Port In - Define o caso de uso de simular empréstimo.
 */
public interface SimularEmprestimoUseCase {

    /**
     * Executa a simulação de empréstimo.
     *
     * @param command dados da simulação (valor e prazo)
     * @return Simulacao com cálculos de parcela, IOF e CET
     */
    Simulacao executar(SimularEmprestimoCommand command);
}
