package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value Object que representa uma taxa de juros mensal.
 * Validada entre 0% e 2.14% (limite legal para consignado).
 * Fornece métodos para conversão para decimal e taxa anual equivalente.
 */
public record TaxaJuros(BigDecimal valorMensal) {

    private static final BigDecimal LIMITE_MAXIMO = new BigDecimal("2.14");

    public TaxaJuros {
        if (valorMensal == null) {
            throw DomainException.invalidField("TaxaJuros", "deve ter valor mensal");
        }

        if (valorMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw DomainException.invalidField("TaxaJuros", "deve ser maior que zero");
        }

        if (valorMensal.compareTo(LIMITE_MAXIMO) > 0) {
            throw DomainException.invalidField("TaxaJuros", "deve ser menor que " + LIMITE_MAXIMO);
        }
    }

    public static TaxaJuros mensal(BigDecimal valorMensal) {
        return new TaxaJuros(valorMensal);
    }

    public static TaxaJuros mensal(String valorMensal) {
        return new TaxaJuros(new BigDecimal(valorMensal));
    }

    /**
     * Converte taxa mensal para decimal (1.66% -> 0.0166)
     */
    public BigDecimal comoDecimal() {
        return valorMensal.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
    }

    public BigDecimal valorAnual() {
        BigDecimal taxaMensal = valorMensal.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
        BigDecimal base = BigDecimal.ONE.add(taxaMensal);
        BigDecimal montanteAnual = base.pow(12);
        BigDecimal jurosAcumulados = montanteAnual.subtract(BigDecimal.ONE);
        BigDecimal resultado = jurosAcumulados.multiply(new BigDecimal("100"));
        return resultado.setScale(2, RoundingMode.HALF_UP);
    }
}
