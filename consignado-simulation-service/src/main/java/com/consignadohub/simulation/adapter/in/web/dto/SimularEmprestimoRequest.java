package com.consignadohub.simulation.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * DTO de entrada para simulação de empréstimo.
 */
public record SimularEmprestimoRequest(
        @NotNull(message = "Valor solicitado é obrigatório")
        @Positive(message = "Valor deve ser maior que zero")
        BigDecimal valorSolicitado,

        @Min(value = 6, message = "Prazo mínimo é 6 meses")
        int prazoMeses
) {}
