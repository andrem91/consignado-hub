package com.consignadohub.simulation.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("SimulaçãoId Value Object")
public class SimulacaoIdTest {

    @Test
    @DisplayName("Deve gerar ID único")
    void deveGerarIdUnico() {
        SimulacaoId id1 = SimulacaoId.gerar();
        SimulacaoId id2 = SimulacaoId.gerar();

        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    @DisplayName("Deve criar a partir de UUID existente")
    void deveCriarDeUuidExistente() {
        UUID uuid = UUID.randomUUID();
        SimulacaoId id = SimulacaoId.of(uuid);

        assertThat(id.valor()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("Deve Rejeitar UUID nulo")
    void deveRejeitarNulo() {
        assertThatThrownBy(() -> SimulacaoId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
