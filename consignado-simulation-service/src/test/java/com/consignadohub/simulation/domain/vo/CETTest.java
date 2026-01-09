package com.consignadohub.simulation.domain.vo;

import com.consignadohub.simulation.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("CET Value Object")
class CETTest {

    @Test
    @DisplayName("Deve criar CET válido")
    void deveCriarCetValido() {
        CET cet = CET.of(new BigDecimal("21.50"));
        assertThat(cet.valorAnual()).isEqualByComparingTo("21.50");
    }

    @Test
    @DisplayName("Deve rejeitar CET zero ou negativo")
    void deveRejeitarCetZeroOuNegativo() {
        assertThatThrownBy(() -> CET.of(BigDecimal.ZERO))
                .isInstanceOf(DomainException.class);
        assertThatThrownBy(() -> CET.of(new BigDecimal("-5")))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve calcular valor mensal corretamente")
    void deveCalcularValorMensal() {
        CET cet = CET.of("21.50");
        // ((1 + 0.215)^(1/12) - 1) * 100 = ~1.63%
        assertThat(cet.valorMensal()).isBetween(new BigDecimal("1.5"), new BigDecimal("1.7"));
    }

    @Test
    @DisplayName("Deve arredondar para 2 casas")
    void deveArredondarPara2Casas() {
        CET cet = CET.of(new BigDecimal("21.555"));
        assertThat(cet.valorAnual()).isEqualByComparingTo("21.56");
    }
}
