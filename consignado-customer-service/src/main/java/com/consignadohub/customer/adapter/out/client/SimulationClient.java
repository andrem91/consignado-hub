package com.consignadohub.customer.adapter.out.client;

import com.consignadohub.customer.adapter.out.client.dto.SimulacaoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Feign Client para comunicação com Simulation Service.
 */
@FeignClient(name = "simulation-service", url = "${services.simulation.url}")
public interface SimulationClient {

    @PostMapping("/simulacoes")
    SimulacaoDTO simular(@RequestBody Map<String, Object> request);

    default SimulacaoDTO simular(BigDecimal valorSolicitado, int prazoMeses) {
        return simular(Map.of(
                "valorSolicitado", valorSolicitado,
                "prazoMeses", prazoMeses));
    }
}
