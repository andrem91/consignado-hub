package com.consignadohub.customer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("TipoBeneficio Enum")
public class TipoBeneficioTest {

    @Test
    @DisplayName("Deve retornar descrição correta")
    void deveRetornarDescricaoCorreta() {
        assertThat(TipoBeneficio.APOSENTADORIA_POR_IDADE.getDescricao())
                .isEqualTo("Aposentadoria por idade");
    }

    @ParameterizedTest
    @EnumSource(TipoBeneficio.class)
    @DisplayName("Deve garantir que todos os enum tem descrição")
    void deveGarantirTodosTemDescricao(TipoBeneficio tipo) {
        assertThat(tipo.getDescricao()).isNotBlank();
    }
}
