package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidPrazoParcelaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("PrazoParcela Value Object")
public class PrazoParcelaTest {

    @Test
    @DisplayName("Deve criar prazo de parcela válido")
    void deveCriarPrazoParcelaValido() {
        PrazoParcela prazoParcela = new PrazoParcela(36);
        assertThat(prazoParcela.meses()).isEqualTo(36);
    }

    @Test
    @DisplayName("Não deve permitir prazo menor que 6 meses")
    void naoDevePermitirPrazoMenorQue6Meses() {
        assertThatThrownBy(() -> PrazoParcela.of(5))
                .isInstanceOf(InvalidPrazoParcelaException.class);
    }

    @Test
    @DisplayName("Não deve permitir prazo maior que 84 meses")
    void naoDevePermitirPrazoMaiorQue86Meses() {
        assertThatThrownBy(() -> PrazoParcela.of(87))
                .isInstanceOf(InvalidPrazoParcelaException.class);
    }
}
