package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.exception.InvalidBeneficioException;
import com.consignadohub.customer.domain.vo.Dinheiro;
import com.consignadohub.customer.domain.vo.NumeroBeneficio;
import com.consignadohub.customer.domain.vo.PercentualMargem;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Beneficio {

    private final UUID id;
    private final NumeroBeneficio numero;
    private final TipoBeneficio tipo;
    private final Dinheiro valorMensal;
    private final LocalDate dataInicio;

    public Beneficio(
            NumeroBeneficio numero,
            TipoBeneficio tipo,
            Dinheiro valorMensal,
            LocalDate dataInicio
    ) {
        if (numero == null) throw new InvalidBeneficioException("Número do benefício é obrigatório");
        if (tipo == null) throw new InvalidBeneficioException("Tipo do benefício é obrigatório");
        if (valorMensal == null) throw new InvalidBeneficioException("Valor do benefício é obrigatório");
        if (dataInicio == null) throw new InvalidBeneficioException("Data de início do benefício é obrigatória");

        if (dataInicio.isAfter(LocalDate.now())) {
            throw new InvalidBeneficioException("Data de início não pode ser futura");
        }

        this.id = UUID.randomUUID();
        this.numero = numero;
        this.tipo = tipo;
        this.valorMensal = valorMensal;
        this.dataInicio = dataInicio;
    }

    public Dinheiro calcularMargemEmprestimo() {
        if (!tipo.isConsignavel()) {
            return Dinheiro.ZERO;
        }
        return PercentualMargem.LIMITE_EMPRESTIMO.calcularMargem(this.valorMensal);
    }
}
