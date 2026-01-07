package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("CET Value Object")
class CETTest {

    @Test
    @DisplayName("Deve criar CET válido")
    void deveCriarCETValido() {
        CET cet = CET.of(new BigDecimal("25.50"));
        assertThat(cet.valorAnual()).isEqualByComparingTo("25.50");
    }

    @Test
    @DisplayName("Não deve permitir CET negativo")
    void naoDevePermitirCETNegativo() {
        assertThatThrownBy(() -> CET.of(new BigDecimal("-5")))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Não deve permitir CET zero")
    void naoDevePermitirCETZero() {
        assertThatThrownBy(() -> CET.of(BigDecimal.ZERO))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Deve converter CET anual para mensal")
    void deveConverterCETAnualParaMensal() {
        CET cet = CET.of(new BigDecimal("26.82"));
        assertThat(cet.valorMensal()).isEqualByComparingTo("2.00");
    }
}