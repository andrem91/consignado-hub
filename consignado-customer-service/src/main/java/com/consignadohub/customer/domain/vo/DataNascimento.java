package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;

import java.time.LocalDate;
import java.time.Period;

public record DataNascimento(LocalDate valor) {

    private static final int IDADE_MINIMA = 18;
    private static final int IDADE_MAXIMA = 120;

    public DataNascimento {
        if (valor == null)
            throw DomainException.required("Data de nascimento");

        if (valor.isAfter(LocalDate.now()))
            throw DomainException.invalidField("Data de nascimento", "não pode ser uma data futura");

        int idade = calcularIdade(valor);

        if (idade < IDADE_MINIMA) {
            throw DomainException.invalidField("Data de nascimento", "deve ser maior de idade");
        }

        if (idade > IDADE_MAXIMA) {
            throw DomainException.invalidField("Data de nascimento", "deve ser menor de 120 anos");
        }

        }

    public int idade() {
        return calcularIdade(valor);
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }



}
