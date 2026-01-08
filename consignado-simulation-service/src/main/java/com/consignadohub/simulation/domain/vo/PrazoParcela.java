package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;

public record PrazoParcela(int meses) {

    public static final int MINIMO = 6;
    public static final int MAXIMO = 84;

    public PrazoParcela {
        if (meses < MINIMO) {
            throw DomainException.invalidField("PrazoParcela", "Mínimo é " + MINIMO + " meses");
        }

        if (meses > MAXIMO) {
            throw DomainException.invalidField("PrazoParcela", "Máximo é " + MAXIMO + " meses");
        }
    }

    public static PrazoParcela of(int meses) {
        return new PrazoParcela(meses);
    }
}
