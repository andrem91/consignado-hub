package com.consignadohub.customer.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("ClienteId Value Object")
public class ClienteIdTest {

    @Test
    @DisplayName("Deve criar ClientId novo")
    void deveCriarClienteIdNovo() {
        ClienteId id = ClienteId.novo();
        assertThat(id.valor()).isNotNull();
    }

    @Test
    @DisplayName("Deve criar ClientId a partir de UUID existente")
    void deveCriarClienteIdDeUUID() {
        UUID uuid = UUID.randomUUID();
        ClienteId id = ClienteId.of(uuid);
        assertThat(id.valor()).isNotNull();
    }

    @Test
    @DisplayName("Deve rejeitar UUID nulo")
    void deveRejeitarUUIDNulo() {
        assertThatThrownBy(() -> ClienteId.of(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
