package com.consignadohub.simulation.config;


import com.consignadohub.simulation.domain.vo.TaxaJuros;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de beans para o Simulation Service.
 */
@Configuration
public class SimulationConfig {

    @Value("${simulation.taxa-padrao:1.66}")
    private String taxaPadrao;

    @Bean
    public TaxaJuros taxaJuros() {
        return TaxaJuros.mensal(taxaPadrao);
    }
}
