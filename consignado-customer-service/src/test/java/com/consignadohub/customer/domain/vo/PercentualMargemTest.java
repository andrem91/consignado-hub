package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("PercentualMargem Value Object")
class PercentualMargemTest {

    @Test
    @DisplayName("Deve criar percentual válido")
    void deveCriarPercentualValido() {
        PercentualMargem margem = PercentualMargem.of(new BigDecimal("30"));
        assertThat(margem.valor()).isEqualByComparingTo("30");
    }

    @Test
    @DisplayName("Não deve permitir valor negativo")
    void naoDevePermitirValorNegativo() {
        assertThatThrownBy(() -> PercentualMargem.of(new BigDecimal("-10")))
                .isInstanceOf(DomainException.class);
    }

    @Test
    @DisplayName("Não deve permitir valor maior que 100")
    void naoDevePermitirValorMaiorQue100() {
        assertThatThrownBy(() -> PercentualMargem.of(new BigDecimal("101")))
                .isInstanceOf(DomainException.class);
    }
}
