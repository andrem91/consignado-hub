package com.consignadohub.customer.adapter.out.client.dto;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para receber resposta do Simulation Service.
 */
public record SimulacaoDTO(
        UUID id,
        BigDecimal valorSolicitado,
        int prazoMeses,
        BigDecimal taxaJurosMensal,
        BigDecimal valorParcela,
        BigDecimal valorTotal,
        BigDecimal iof,
        BigDecimal cetAnual
) {}
