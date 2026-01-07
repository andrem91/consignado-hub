package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.exception.DomainException;
import com.consignadohub.customer.domain.vo.Dinheiro;
import com.consignadohub.customer.domain.vo.NumeroBeneficio;
import com.consignadohub.customer.domain.vo.PercentualMargem;
import lombok.Getter;

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
        if (numero == null) throw DomainException.required("Número do benefício");
        if (tipo == null) throw DomainException.required("Tipo do benefício");
        if (valorMensal == null) throw DomainException.required("Valor mensal do benefício");
        if (dataInicio == null) throw DomainException.required("Data de início do benefício");

        if (dataInicio.isAfter(LocalDate.now())) {
            throw DomainException.invalidField("dataInicio", "Data de início não pode ser futura");
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
