package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidDataNascimentoException;

import java.time.LocalDate;
import java.time.Period;

public record DataNascimento(LocalDate valor) {

    private static final int IDADE_MINIMA = 18;
    private static final int IDADE_MAXIMA = 120;

    public DataNascimento {
        if (valor == null)
            throw new InvalidDataNascimentoException("Data de nascimento não pode ser nula");

        if (valor.isAfter(LocalDate.now()))
            throw new InvalidDataNascimentoException("Data de nascimento não pode ser futura");

        int idade = calcularIdade(valor);

        if (idade < IDADE_MINIMA) {
            throw new InvalidDataNascimentoException("Idade mínima é " + IDADE_MINIMA);
        }

        if (idade > IDADE_MAXIMA) {
            throw new InvalidDataNascimentoException("Idade máxima é " + IDADE_MAXIMA);
        }

        }

    public int idade() {
        return calcularIdade(valor);
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }



}
