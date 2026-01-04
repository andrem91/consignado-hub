package com.consignadohub.customer.domain.model;

import com.consignadohub.customer.domain.vo.Dinheiro;
import com.consignadohub.customer.domain.vo.NumeroBeneficio;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Beneficio {

    private static final BigDecimal PERCENTUAL_MARGEM_EMPRESTIMO = new BigDecimal("0.35");

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
        this.id = UUID.randomUUID();
        this.numero = numero;
        this.tipo = tipo;
        this.valorMensal = valorMensal;
        this.dataInicio = dataInicio;
    }

    public Dinheiro calcularMargemEmprestimo() {
        return Dinheiro.of(valorMensal.valor()
                .multiply(PERCENTUAL_MARGEM_EMPRESTIMO)
        );
    }


}
