package com.consignadohub.simulation.adapter.in.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO de saída com resultado da simulação.
 */
public record SimulacaoResponse(
        UUID id,
        BigDecimal valorSolicitado,
        int prazoMeses,
        BigDecimal taxaJurosMensal,
        BigDecimal valorParcela,
        BigDecimal valorTotal,
        BigDecimal iof,
        BigDecimal cetAnual
) {}
