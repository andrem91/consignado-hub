package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidPrazoParcelaException;

public record PrazoParcela(int meses) {
    public static final int MINIMO = 6;
    public static final int MAXIMO = 84;

    public PrazoParcela {
        if(meses < MINIMO) {
            throw new InvalidPrazoParcelaException("Prazo mínimo é " + MINIMO + " meses");
        }

        if(meses > MAXIMO) {
            throw new InvalidPrazoParcelaException("Prazo máximo é " + MAXIMO + " meses");
        }
    }

    public static PrazoParcela of(int meses) {
        return new PrazoParcela(meses);
    }
}
