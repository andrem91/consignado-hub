package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("PrazoParcela Value Object")
class PrazoParcelaTest {

    @Test
    @DisplayName("Deve criar prazo válido (24 meses)")
    void deveCriarPrazoValido() {
        PrazoParcela prazo = PrazoParcela.of(24);
        assertThat(prazo.meses()).isEqualTo(24);
    }

    @Test
    @DisplayName("Deve rejeitar prazo abaixo do mínimo (6)")
    void deveRejeitarPrazoAbaixoDoMinimo() {
        assertThatThrownBy(() -> PrazoParcela.of(5))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve rejeitar prazo acima do máximo (84)")
    void deveRejeitarPrazoAcimaDoMaximo() {
        assertThatThrownBy(() -> PrazoParcela.of(85))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve aceitar prazo mínimo (6)")
    void deveAceitarPrazoMinimo() {
        PrazoParcela prazo = PrazoParcela.of(6);
        assertThat(prazo.meses()).isEqualTo(6);
    }

    @Test
    @DisplayName("Deve aceitar prazo máximo (84)")
    void deveAceitarPrazoMaximo() {
        PrazoParcela prazo = PrazoParcela.of(84);
        assertThat(prazo.meses()).isEqualTo(84);
    }
}
