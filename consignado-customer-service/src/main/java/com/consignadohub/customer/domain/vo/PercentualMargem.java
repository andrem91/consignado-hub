package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidPercentualMargemException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record PercentualMargem(BigDecimal valor) {

    public static final PercentualMargem LIMITE_EMPRESTIMO = PercentualMargem.of(new BigDecimal("35"));
    public static final PercentualMargem LIMITE_CARTAO = PercentualMargem.of(new BigDecimal("5"));

    public PercentualMargem {
        if (valor == null) {
            throw new InvalidPercentualMargemException("Valor não pode ser nulo");
        }
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPercentualMargemException("Valor não pode ser negativo");
        }
        if (valor.compareTo(new BigDecimal("100")) > 0) {
            throw new InvalidPercentualMargemException("Valor não pode ser maior que 100");
        }
    }

    public static PercentualMargem of (BigDecimal valor) {
        return new PercentualMargem(valor);
    }

    public Dinheiro calcularMargem(Dinheiro valorBase) {
        BigDecimal fator = this.valor.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        return Dinheiro.of(valorBase.valor().multiply(fator));
    }
}
