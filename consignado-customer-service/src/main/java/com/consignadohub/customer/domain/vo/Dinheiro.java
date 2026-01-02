package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidDinheiroException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Dinheiro(BigDecimal valor) {

    public static final Dinheiro ZERO = new Dinheiro(BigDecimal.ZERO);

    public Dinheiro {
        if (valor == null) {
            throw new InvalidDinheiroException("Valor não pode ser nulo ou vazio");
        }

        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDinheiroException("Valor não pode ser negativo");
        }

        valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static Dinheiro of(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public static Dinheiro of(String valor) {
        return new Dinheiro(new BigDecimal(valor));
    }

    public Dinheiro somar(Dinheiro dinheiro) {
        return new Dinheiro(this.valor.add(dinheiro.valor));
    }
}
