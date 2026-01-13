package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public record Dinheiro(BigDecimal valor) implements Comparable<Dinheiro> {

    private static final String FIELD_NAME = "Dinheiro";
    public static final Dinheiro ZERO = new Dinheiro(BigDecimal.ZERO);
    private static final NumberFormat FORMATTER = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public Dinheiro {
        if (valor == null) {
            throw DomainException.required(FIELD_NAME);
        }

        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw DomainException.invalidField(FIELD_NAME, "não pode ser negativo");
        }

        valor = valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static Dinheiro of(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public static Dinheiro of(String valor) {
        return new Dinheiro(new BigDecimal(valor));
    }

    public Dinheiro somar(Dinheiro outro) {
        validarOperando(outro);
        return new Dinheiro(this.valor.add(outro.valor));
    }

    public Dinheiro subtrair(Dinheiro outro) {
        validarOperando(outro);
        return new Dinheiro(this.valor.subtract(outro.valor));
    }

    public Dinheiro multiplicar(int quantidade) {
        return new Dinheiro(this.valor.multiply(BigDecimal.valueOf(quantidade)));
    }

    public boolean maiorQue(Dinheiro outro) {
        validarOperando(outro);
        return this.valor.compareTo(outro.valor) > 0;
    }

    public boolean menorOuIgual(Dinheiro outro) {
        return !maiorQue(outro);
    }

    public String formatado() {
        return FORMATTER.format(this.valor);
    }

    @Override
    public int compareTo(Dinheiro o) {
        return this.valor.compareTo(o.valor);
    }

    private void validarOperando(Dinheiro outro) {
        if (outro == null) {
            throw DomainException.required(FIELD_NAME);
        }

    }

}
