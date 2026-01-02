package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidTaxaJurosException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record TaxaJuros(BigDecimal valorMensal) {

    private static final BigDecimal LIMITE_MAXIMO = new BigDecimal("2.14");

    public TaxaJuros {
        if (valorMensal == null) {
            throw new InvalidTaxaJurosException("Valor mensal não pode ser nulo");
        }

        if (valorMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTaxaJurosException("Valor mensal não pode ser negativo");
        }

        if (valorMensal.compareTo(LIMITE_MAXIMO) > 0) {
            throw new InvalidTaxaJurosException("Valor mensal não pode ser maior que " + LIMITE_MAXIMO);
        }
    }

    public static TaxaJuros of(BigDecimal valorMensal) {
        return new TaxaJuros(valorMensal);
    }

    public BigDecimal valorAnual() {
        // 1. valorMensal / 100
        BigDecimal taxaMensal = valorMensal.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);

        // 2. (1 + valorMensal/100)
        BigDecimal base = BigDecimal.ONE.add(taxaMensal);

        // 3. (1 + valorMensal/100)^12
        // pow() aceita apenas int, ideal para expoentes inteiros como 12
        BigDecimal montanteAnual = base.pow(12);

        // 4. ((1 + valorMensal/100)^12 - 1)
        BigDecimal jurosAcumulados = montanteAnual.subtract(BigDecimal.ONE);

        // 5. ((1 + valorMensal/100)^12 - 1) * 100
        BigDecimal resultado = jurosAcumulados.multiply(new BigDecimal("100"));

        // Opcional: Arredondar para 2 casas decimais no final
        return resultado.setScale(2, RoundingMode.HALF_UP);
    }
}
