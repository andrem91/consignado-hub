package com.consignadohub.customer.domain.vo;

import com.consignadohub.customer.domain.exception.InvalidDinheiroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Dinheiro Value Object")
public class DinheiroTest {

    @Test
    @DisplayName("Deve criar Dinheiro com valor positivo")
    void deveCriarDinheiroComValorPositivo() {
        Dinheiro dinheiro = Dinheiro.of(new BigDecimal("1000.50"));
        assertThat(dinheiro.valor()).isEqualByComparingTo("1000.50");
    }

    @Test
    @DisplayName("Deve arredondar para 2 casas decimais")
    void deveArredondar() {
        Dinheiro dinheiro = Dinheiro.of(new BigDecimal("1000.4999"));
        assertThat(dinheiro.valor()).isEqualByComparingTo("1000.50");

    }

    @Test
    @DisplayName("Não deve permitir valor negativo")
    void naoDevePermitirValorNegativo() {
        assertThatThrownBy(() -> Dinheiro.of(new BigDecimal("-100")))
                .isInstanceOf(InvalidDinheiroException.class);
    }

    @Test
    @DisplayName("Deve somar valores mantendo imutabilidade")
    void deveSomar() {
        Dinheiro a = Dinheiro.of(new BigDecimal("100"));
        Dinheiro b = Dinheiro.of(new BigDecimal("50"));

        Dinheiro resultado = a.somar(b);

        assertThat(resultado.valor()).isEqualByComparingTo("150");
        assertThat(a.valor()).isEqualByComparingTo("100");
    }
}
