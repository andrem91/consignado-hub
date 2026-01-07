package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record CET(BigDecimal valorAnual) {

    public CET {

        if (valorAnual == null) {
            throw DomainException.required("CET");
        }

        if(valorAnual.compareTo(BigDecimal.ZERO) <= 0) {
            throw DomainException.required("CET");
        }

        valorAnual = valorAnual.setScale(2, RoundingMode.HALF_UP);
    }

    public static CET of(BigDecimal valorAnual) {
        return new CET(valorAnual);
    }

    public BigDecimal valorMensal() {
        // Fórmula: ((1 + CET/100)^(1/12) - 1) * 100
        BigDecimal cetDecimal = valorAnual.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
        BigDecimal base = BigDecimal.ONE.add(cetDecimal);
        double mensal = Math.pow(base.doubleValue(), 1.0/12.0) - 1;
        return BigDecimal.valueOf(mensal * 100).setScale(2, RoundingMode.HALF_UP);
    }
}
