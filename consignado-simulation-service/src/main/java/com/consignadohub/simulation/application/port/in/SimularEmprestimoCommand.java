package com.consignadohub.simulation.application.port.in;

import com.consignadohub.simulation.application.exception.BusinessException;

import java.math.BigDecimal;

public record SimularEmprestimoCommand(
        BigDecimal valorSolicitado,
        int prazoMeses
) {
    private static final int PRAZO_MINIMO = 6;
    private static final int PRAZO_MAXIMO = 84;

    public SimularEmprestimoCommand {
        if(prazoMeses < PRAZO_MINIMO) {
            throw new BusinessException("Prazo mínimo é de " + PRAZO_MINIMO + " meses");
        }
        if (prazoMeses > PRAZO_MAXIMO) {
            throw new BusinessException("Prazo máximo é de " + PRAZO_MAXIMO + " meses");
        }
        if (valorSolicitado == null) {
            throw new BusinessException("Valor solicitado é obrigatório");
        }
        if (valorSolicitado.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor solicitado deve ser maior que zero");
        }

    }
}
