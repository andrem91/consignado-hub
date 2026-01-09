package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;
@DisplayName("SimulacaoId Value Object")
class SimulacaoIdTest {
    @Test
    @DisplayName("Deve gerar ID único")
    void deveGerarIdUnico() {
        SimulacaoId id1 = SimulacaoId.gerar();
        SimulacaoId id2 = SimulacaoId.gerar();

        assertThat(id1).isNotNull();
        assertThat(id1.valor()).isNotNull();
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
    @DisplayName("Deve rejeitar UUID nulo")
    void deveRejeitarNulo() {
        assertThatThrownBy(() -> SimulacaoId.of(null))
                .isInstanceOf(DomainException.class);
    }
}